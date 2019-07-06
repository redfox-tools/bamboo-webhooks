package tools.redfox.bamboo.webhooks.listener.action;

import com.atlassian.bamboo.chains.StageExecution;
import com.atlassian.bamboo.v2.build.BuildContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.factory.JobEventFactory;
import tools.redfox.bamboo.webhooks.service.WebhookHandler;

public class PreJobAction implements com.atlassian.bamboo.chains.plugins.PreJobAction {
    private WebhookHandler handler;
    private JobEventFactory jobEventFactory;

    @Autowired
    public PreJobAction(WebhookHandler handler, JobEventFactory jobEventFactory) {
        this.handler = handler;
        this.jobEventFactory = jobEventFactory;
    }

    @Override
    public void execute(@NotNull StageExecution stageExecution, @NotNull BuildContext buildContext) {
        handler.notify(
                jobEventFactory.createJobStartedEvent(stageExecution, buildContext)
        );
    }
}
