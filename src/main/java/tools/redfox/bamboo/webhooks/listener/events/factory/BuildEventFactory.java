package tools.redfox.bamboo.webhooks.listener.events.factory;

import com.atlassian.bamboo.chains.Chain;
import com.atlassian.bamboo.chains.ChainExecution;
import com.atlassian.bamboo.chains.ChainResultsSummary;
import com.atlassian.bamboo.chains.ChainStageResult;
import com.atlassian.bamboo.configuration.AdministrationConfigurationAccessor;
import com.atlassian.bamboo.resultsummary.BuildResultsSummary;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.plugin.spring.scanner.annotation.component.BambooComponent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.BuildFinishedEvent;
import tools.redfox.bamboo.webhooks.listener.events.BuildStartedEvent;
import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Plan;
import tools.redfox.bamboo.webhooks.listener.events.model.Stage;

@BambooComponent
public class BuildEventFactory extends AbstractFactory {
    @Autowired
    public BuildEventFactory(AdministrationConfigurationAccessor administrationConfigurationAccessor) {
        super(administrationConfigurationAccessor);
    }

    public BuildStartedEvent createBuildStartedEvent(Chain chain, ChainExecution chainExecution) {
        @NotNull BuildContext plan = (BuildContext) chainExecution.getBuildIdentifier();
        Build build = new Build(
                plan,
                getAbsoluteUrlFor("/browse/" + plan.getBuildResultKey())
        );

        return new BuildStartedEvent(
                chain.getProject().getName(),
                new Plan(plan.getPlanName(), plan.getPlanKey(), getAbsoluteUrlFor("/browse/" + plan.getPlanKey())),
                build
        );
    }

    public BuildFinishedEvent createBuildFinishedEvent(Chain chain, ChainResultsSummary result) {
        Build build = new Build(
                result,
                getAbsoluteUrlFor("/browse/" + result.getBuildResultKey())
        );
        build.setStatus(result.isFinished() ? (result.isSuccessful() ? "SUCCESS" : "FAILED") : "CANCELLED");
        build.setSummary(result.getShortReasonSummary());

        for (ChainStageResult stage : result.getStageResults()) {
            Stage s = new Stage(stage.getName());
            for (BuildResultsSummary summary : stage.getBuildResults()) {
                s.addJob(getJobFor(summary));
            }

            build.addStage(s);
        }


        return new BuildFinishedEvent(
                chain.getProject().getName(),
                new Plan(result.getPlanName(), result.getPlanKey().getKey(), getAbsoluteUrlFor("/browse/" + result.getPlanKey())),
                build
        );
    }
}
