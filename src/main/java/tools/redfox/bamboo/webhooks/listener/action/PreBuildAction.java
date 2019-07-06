package tools.redfox.bamboo.webhooks.listener.action;

import com.atlassian.bamboo.v2.build.BuildContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.factory.BuildEventFactory;
import tools.redfox.bamboo.webhooks.service.WebhookHandler;

public class PreBuildAction extends AbstractBuildAction {
    private WebhookHandler handler;
    private BuildEventFactory buildEventFactory;

    @Autowired
    public PreBuildAction(WebhookHandler handler, BuildEventFactory buildEventFactory) {
        this.handler = handler;
        this.buildEventFactory = buildEventFactory;
    }

    @Override
    public @NotNull BuildContext call() throws InterruptedException, Exception {
        this.handler.notify(
                buildEventFactory.createBuildStartedEvent(buildContext),
                buildContext
        );
        return super.call();
    }
}
