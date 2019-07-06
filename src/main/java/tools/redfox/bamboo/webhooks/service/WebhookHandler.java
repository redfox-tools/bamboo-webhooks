package tools.redfox.bamboo.webhooks.service;

import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.plugin.spring.scanner.annotation.component.BambooComponent;
import kong.unirest.Unirest;
import tools.redfox.bamboo.webhooks.listener.events.AbstractEvent;

@BambooComponent
public class WebhookHandler {
    public void notify(AbstractEvent event, BuildContext buildContext) {
        Unirest.post("http://requestbin.fullcontact.com/1mdfc8f1")
                .header("Content-Type", "application/json")
                .header("X-Bamboo-Event-Type", event.getClass().getSimpleName().replace("Event", ""))
                .header("User-Agent", "BambooWebHooks/6.9.1")
                .body(event)
                .asEmpty();
    }
}
