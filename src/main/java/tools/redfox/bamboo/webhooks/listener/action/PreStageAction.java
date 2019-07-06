package tools.redfox.bamboo.webhooks.listener.action;

import com.atlassian.bamboo.chains.StageExecution;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.factory.StageEventFactory;
import tools.redfox.bamboo.webhooks.service.WebhookHandler;

public class PreStageAction implements com.atlassian.bamboo.chains.plugins.PreStageAction {
    private WebhookHandler handler;
    private StageEventFactory stageEventFactory;

    @Autowired
    public PreStageAction(WebhookHandler handler, StageEventFactory stageEventFactory) {
        this.handler = handler;
        this.stageEventFactory = stageEventFactory;
    }

    @Override
    public void execute(@NotNull StageExecution stageExecution) throws InterruptedException, Exception {
        handler.notify(
                stageEventFactory.createStageStartedEvent(stageExecution)
        );
    }
}
