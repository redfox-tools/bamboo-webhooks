package tools.redfox.bamboo.webhooks.listener.events.factory;

import com.atlassian.bamboo.chains.BuildExecution;
import com.atlassian.bamboo.chains.StageExecution;
import com.atlassian.bamboo.configuration.AdministrationConfigurationAccessor;
import com.atlassian.bamboo.resultsummary.BuildResultsSummary;
import com.atlassian.bamboo.resultsummary.ResultsSummary;
import com.atlassian.bamboo.utils.BambooUrl;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.UrlMode;
import org.apache.commons.lang3.time.DurationFormatUtils;
import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Job;
import tools.redfox.bamboo.webhooks.listener.events.model.Plan;
import tools.redfox.bamboo.webhooks.listener.events.model.Stage;

abstract public class AbstractFactory {
    protected static BambooUrl bambooUrl;

    public AbstractFactory(@ComponentImport AdministrationConfigurationAccessor administrationConfigurationAccessor) {
        bambooUrl = new BambooUrl(administrationConfigurationAccessor);
    }

    protected Plan getPlanFor(BuildContext context) {
        BuildContext planContext = getPlanBuildContext(context);
        return new Plan(
                planContext.getPlanName(),
                planContext.getPlanKey(),
                getAbsoluteUrlFor("/browse/" + planContext.getPlanKey())
        );
    }

    protected Build getBuildFor(BuildContext context) {
        return new Build(
                context,
                getAbsoluteUrlFor("/browse/" + context.getBuildResultKey())
        );
    }

    protected Stage getStageFor(StageExecution stageExecution) {
        return new Stage(
                stageExecution
        );
    }

    public static String getAbsoluteUrlFor(String path) {
        return bambooUrl.getBaseUrl(UrlMode.ABSOLUTE).concat(path);
    }

    public static BuildExecution getCurrentBuildContext(StageExecution stageExecution) {
        return stageExecution.getBuilds()
                .stream()
                .filter(b -> ((BuildContext) b.getBuildIdentifier()).getBuildKey().equals(((BuildContext) stageExecution.getChainExecution().getBuildIdentifier()).getBuildKey()))
                .findFirst()
                .get();
    }

    public BuildContext getPlanBuildContext(BuildContext buildContext) {
        while (buildContext.getParentBuildContext() != null) {
            buildContext = buildContext.getParentBuildContext();
        }
        return buildContext;
    }

    protected Job getJobFor(BuildContext context) {
        return new Job(
                context,
                getAbsoluteUrlFor("/browse/" + context.getBuildResultKey())
        );
    }

    protected Job getJobFor(BuildResultsSummary summary) {
        return applySummary(
                new Job(
                        summary.getPlanName(),
                        getAbsoluteUrlFor("/browse/" + summary.getBuildResultKey())
                ), summary
        );
    }

    protected Job applySummary(Job job, ResultsSummary summary) {
        job.setSummary(summary.getTestSummary());
        job.setDuration(DurationFormatUtils.formatDuration(
                summary.getDuration(),
                "H:mm:ss",
                true
        ));
        job.setStatus(
                !summary.isFinished() || summary.getBuildCancelledDate() != null ? "CANCELED" :
                        summary.isSuccessful() ? "SUCCESS" : "FAILED"
        );

        return job;
    }
}
