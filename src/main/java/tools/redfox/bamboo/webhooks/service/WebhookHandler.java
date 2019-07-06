package tools.redfox.bamboo.webhooks.service;

import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.plan.PlanManager;
import com.atlassian.plugin.PluginAccessor;
import com.atlassian.plugin.spring.scanner.annotation.component.BambooComponent;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import kong.unirest.Unirest;
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
        String destination = getDestinationUrl(event);
        if (String.valueOf(destination).trim().isEmpty()) {
            return;
        }

        log.info(String.format("Sending %s event to %s", event.getClass().getSimpleName(), destination));

        Unirest.post(destination)
                .header("Content-Type", "application/json")
                .header("X-Bamboo-Event-Type", event.getClass().getSimpleName().replace("Event", ""))
                .header("User-Agent", String.format("BambooWebHooks/%s", this.pluginVersion))
                .body(event)
                .asEmpty();
    }

    protected String getDestinationUrl(AbstractEvent event) {
        Plan plan = planManager.getPlanByKey(event.getPlan().getKey());
        if (plan.getBuildDefinition().getCustomConfiguration().getOrDefault("custom.bamboo.webhook.mode", "1").equals("1")) {
            return plan.getBuildDefinition().getCustomConfiguration().getOrDefault("custom.bamboo.webhook.global", "");
        } else {
            return plan.getBuildDefinition().getCustomConfiguration().getOrDefault(event.getConfigKey(), "");
        }
    }
}
