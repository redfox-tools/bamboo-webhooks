package tools.redfox.bamboo.webhooks.listener.events.factory;

import com.atlassian.bamboo.chains.ChainResultsSummary;
import com.atlassian.bamboo.chains.ChainStageResult;
import com.atlassian.bamboo.chains.StageExecution;
import com.atlassian.bamboo.configuration.AdministrationConfigurationAccessor;
import com.atlassian.bamboo.resultsummary.BuildResultsSummary;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.plugin.spring.scanner.annotation.component.BambooComponent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.StageFinishedEvent;
import tools.redfox.bamboo.webhooks.listener.events.StageStartedEvent;
import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Stage;

@BambooComponent
public class StageEventFactory extends AbstractFactory {
    @Autowired
    public StageEventFactory(AdministrationConfigurationAccessor administrationConfigurationAccessor) {
        super(administrationConfigurationAccessor);
    }

    public StageStartedEvent createStageStartedEvent(StageExecution stageExecution) {
        @NotNull BuildContext buildContext = getCurrentBuildContext(stageExecution).getBuildContext();

        Build build = getBuildFor(buildContext.getParentBuildContext());
        build.setStage(getStageFor(stageExecution));

        return new StageStartedEvent(
                buildContext.getProjectName(),
                getPlanFor(buildContext),
                build
        );
    }

    public StageFinishedEvent createStageFinishedEvent(ChainResultsSummary chainResultsSummary, ChainStageResult chainStageResult, StageExecution stageExecution) {
        @NotNull BuildContext buildContext = getCurrentBuildContext(stageExecution).getBuildContext();

        Build build = getBuildFor(buildContext.getParentBuildContext());
        Stage stage = getStageFor(stageExecution);

        for(BuildResultsSummary summary : chainStageResult.getBuildResults()) {
            stage.addJob(
                    getJobFor(
                            summary
                    )
            );
        }

        build.setStage(stage);

        return new StageFinishedEvent(
                buildContext.getProjectName(),
                getPlanFor(buildContext),
                build
        );
    }
}
