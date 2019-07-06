package tools.redfox.bamboo.webhooks.listener.action;

import com.atlassian.bamboo.chains.ChainResultsSummary;
import com.atlassian.bamboo.chains.ChainStageResult;
import com.atlassian.bamboo.chains.StageExecution;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.factory.StageEventFactory;
import tools.redfox.bamboo.webhooks.service.WebhookHandler;

import static tools.redfox.bamboo.webhooks.listener.events.factory.AbstractFactory.getCurrentBuildContext;

public class PostStageAction implements com.atlassian.bamboo.chains.plugins.PostStageAction {
    private WebhookHandler handler;
    private StageEventFactory stageEventFactory;

    @Autowired
    public PostStageAction(WebhookHandler handler, StageEventFactory stageEventFactory) {
        this.handler = handler;
        this.stageEventFactory = stageEventFactory;
    }

    @Override
    public void execute(@NotNull ChainResultsSummary chainResultsSummary, @NotNull ChainStageResult chainStageResult, @NotNull StageExecution stageExecution) throws InterruptedException, Exception {
        handler.notify(
                stageEventFactory.createStageFinishedEvent(chainResultsSummary, chainStageResult, stageExecution),
                getCurrentBuildContext(stageExecution).getBuildContext()
        );
    }
}
