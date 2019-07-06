package tools.redfox.bamboo.webhooks.service;

import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.plan.PlanManager;
import com.atlassian.plugin.PluginAccessor;
import com.atlassian.plugin.spring.scanner.annotation.component.BambooComponent;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import kong.unirest.ObjectMapper;
import kong.unirest.RequestBodyEntity;
import kong.unirest.Unirest;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.AbstractEvent;

@BambooComponent
public class WebhookHandler {
    private final String pluginVersion;
    private PlanManager planManager;

    private static final Logger log = LoggerFactory.getLogger(WebhookHandler.class);

    @Autowired
    public WebhookHandler(@ComponentImport PlanManager planManager, @ComponentImport PluginAccessor pluginAccessor) {
        this.planManager = planManager;
        this.pluginVersion = pluginAccessor.getPlugin("tools.redfox.bamboo.webhooks").getPluginInformation().getVersion();
    }

    public void notify(AbstractEvent event) {
        Plan plan = planManager.getPlanByKey(event.getPlan().getKey());
        String destination = getDestinationUrl(plan, event);
        if (String.valueOf(destination).trim().isEmpty()) {
            return;
        }

        log.info(String.format("Sending %s event to %s", event.getClass().getSimpleName(), destination));

        ObjectMapper mapper = Unirest.config().getObjectMapper();
        String body = mapper.writeValue(event);
        String key = plan.getBuildDefinition().getCustomConfiguration().get("custom.bamboo.webhook.secret");

        RequestBodyEntity request = Unirest.post(destination)
                .header("Content-Type", "application/json")
                .header("X-Bamboo-Event-Type", event.getClass().getSimpleName().replace("Event", ""))
                .header("User-Agent", String.format("BambooWebHooks/%s", this.pluginVersion))
                .body(body);

        if (!key.isEmpty()) {
            String signature = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmacHex(body);
            request.header("X-Signature", signature);
        }

        request.asEmpty();
    }

    protected String getDestinationUrl(Plan plan, AbstractEvent event) {
        if (plan.getBuildDefinition().getCustomConfiguration().getOrDefault("custom.bamboo.webhook.mode", "1").equals("1")) {
            return plan.getBuildDefinition().getCustomConfiguration().getOrDefault("custom.bamboo.webhook.global", "");
        } else {
            return plan.getBuildDefinition().getCustomConfiguration().getOrDefault(event.getConfigKey(), "");
        }
    }
}
