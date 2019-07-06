package tools.redfox.bamboo.webhooks.listener.events.factory;

import com.atlassian.bamboo.chains.StageExecution;
import com.atlassian.bamboo.configuration.AdministrationConfigurationAccessor;
import com.atlassian.bamboo.resultsummary.BuildResultsSummary;
import com.atlassian.bamboo.resultsummary.ResultsSummary;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.plugin.spring.scanner.annotation.component.BambooComponent;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.JobFinishedEvent;
import tools.redfox.bamboo.webhooks.listener.events.JobStartedEvent;
import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Job;

@BambooComponent
public class JobEventFactory extends AbstractFactory {
    @Autowired
    public JobEventFactory(AdministrationConfigurationAccessor administrationConfigurationAccessor) {
        super(administrationConfigurationAccessor);
    }

    public JobStartedEvent createJobStartedEvent(StageExecution stageExecution, BuildContext buildContext) {
        Build build = getBuildFor(buildContext.getParentBuildContext()).setStage(
                getStageFor(stageExecution).setJob(getJobFor(buildContext))
        );

        return new JobStartedEvent(
                buildContext.getProjectName(),
                getPlanFor(buildContext),
                build
        );
    }

    public JobFinishedEvent createJobFinishedEvent(StageExecution stageExecution, com.atlassian.bamboo.build.Job job, BuildResultsSummary buildResultsSummary) {
        @NotNull BuildContext buildContext = getCurrentBuildContext(stageExecution).getBuildContext();
        @Nullable ResultsSummary jobStatus = job.getLatestResultsSummary();

        Job jobModel = applySummary(getJobFor(buildContext), jobStatus);

        Build build = getBuildFor(buildContext.getParentBuildContext()).setStage(
                getStageFor(stageExecution).setJob(jobModel)
        );

        return new JobFinishedEvent(
                job.getProject().getName(),
                getPlanFor(buildContext),
                build
        );
    }
}
