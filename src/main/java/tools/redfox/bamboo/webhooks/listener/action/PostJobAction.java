package tools.redfox.bamboo.webhooks.listener.action;

import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.chains.StageExecution;
import com.atlassian.bamboo.resultsummary.BuildResultsSummary;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.factory.JobEventFactory;
import tools.redfox.bamboo.webhooks.service.WebhookHandler;

public class PostJobAction implements com.atlassian.bamboo.chains.plugins.PostJobAction {
    private WebhookHandler handler;
    private JobEventFactory jobEventFactory;

    @Autowired
    public PostJobAction(WebhookHandler handler, JobEventFactory jobEventFactory) {
        this.handler = handler;
        this.jobEventFactory = jobEventFactory;
    }

    @Override
    public void execute(@NotNull StageExecution stageExecution, @NotNull Job job, @NotNull BuildResultsSummary buildResultsSummary) {
        handler.notify(
                jobEventFactory.createJobFinishedEvent(stageExecution, job, buildResultsSummary)
        );
    }
}
