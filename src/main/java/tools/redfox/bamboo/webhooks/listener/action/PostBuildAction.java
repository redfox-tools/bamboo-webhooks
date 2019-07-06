package tools.redfox.bamboo.webhooks.listener.action;

import com.atlassian.bamboo.chains.Chain;
import com.atlassian.bamboo.chains.ChainExecution;
import com.atlassian.bamboo.chains.ChainResultsSummary;
import com.atlassian.bamboo.chains.plugins.PostChainAction;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.factory.BuildEventFactory;
import tools.redfox.bamboo.webhooks.service.WebhookHandler;

public class PostBuildAction implements PostChainAction {
    private WebhookHandler handler;
    private BuildEventFactory buildEventFactory;

    @Autowired
    public PostBuildAction(WebhookHandler handler, BuildEventFactory buildEventFactory) {
        this.handler = handler;
        this.buildEventFactory = buildEventFactory;
    }

    @Override
    public void execute(@NotNull Chain chain, @NotNull ChainResultsSummary chainResultsSummary, @NotNull ChainExecution chainExecution) throws InterruptedException, Exception {
        this.handler.notify(
                buildEventFactory.createBuildFinishedEvent(chain, chainResultsSummary)
        );
    }
}
