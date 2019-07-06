package tools.redfox.bamboo.webhooks.listener.action;

import com.atlassian.bamboo.chains.Chain;
import com.atlassian.bamboo.chains.ChainExecution;
import com.atlassian.bamboo.chains.plugins.PreChainAction;
import com.atlassian.bamboo.v2.build.BuildContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.factory.BuildEventFactory;
import tools.redfox.bamboo.webhooks.service.WebhookHandler;

public class PreBuildAction implements PreChainAction {
    private WebhookHandler handler;
    private BuildEventFactory buildEventFactory;

    @Autowired
    public PreBuildAction(WebhookHandler handler, BuildEventFactory buildEventFactory) {
        this.handler = handler;
        this.buildEventFactory = buildEventFactory;
    }

    @Override
    public void execute(@NotNull Chain chain, @NotNull ChainExecution chainExecution) throws InterruptedException, Exception {
        this.handler.notify(
                buildEventFactory.createBuildStartedEvent(chain, chainExecution),
                (BuildContext) chainExecution.getBuildIdentifier()
        );
    }
}
