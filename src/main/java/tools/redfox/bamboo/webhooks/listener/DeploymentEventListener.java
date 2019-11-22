package tools.redfox.bamboo.webhooks.listener;

import com.atlassian.bamboo.deployments.execution.events.DeploymentFinishedEvent;
import com.atlassian.bamboo.deployments.execution.events.DeploymentStartedEvent;
import com.atlassian.bamboo.deployments.results.DeploymentResult;
import com.atlassian.bamboo.deployments.results.service.DeploymentResultService;
import com.atlassian.bamboo.deployments.versions.DeploymentVersion;
import com.atlassian.bamboo.deployments.versions.events.DeploymentVersionCreatedEvent;
import com.atlassian.bamboo.deployments.versions.service.DeploymentVersionService;
import com.atlassian.bamboo.plan.PlanResultKey;
import com.atlassian.bamboo.resultsummary.ResultsSummary;
import com.atlassian.bamboo.resultsummary.ResultsSummaryManager;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.VersionCreatedEvent;
import tools.redfox.bamboo.webhooks.listener.events.factory.BuildEventFactory;
import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Plan;
import tools.redfox.bamboo.webhooks.service.WebhookHandler;

public class DeploymentEventListener {
    private final DeploymentVersionService deploymentVersionService;
    private final DeploymentResultService deploymentResultService;
    private final ResultsSummaryManager resultsSummaryManager;
    private final WebhookHandler handler;

    @Autowired
    public DeploymentEventListener(
            @ComponentImport DeploymentVersionService deploymentVersionService,
            @ComponentImport DeploymentResultService deploymentResultService,
            @ComponentImport ResultsSummaryManager resultsSummaryManager,
            WebhookHandler handler
    ) {
        this.deploymentVersionService = deploymentVersionService;
        this.deploymentResultService = deploymentResultService;
        this.resultsSummaryManager = resultsSummaryManager;
        this.handler = handler;
    }

    @EventListener
    public void onDeploymentStarted(DeploymentStartedEvent event) {
        @Nullable DeploymentResult result = deploymentResultService.getDeploymentResult(event.getDeploymentResultId());
        @Nullable DeploymentVersion version = result.getDeploymentVersion();
        PlanResultKey planKey = deploymentVersionService.getRelatedPlanResultKeys(version.getId()).stream().findFirst().get();
        @Nullable ResultsSummary results = resultsSummaryManager.getResultsSummary(planKey);

        handler.notify(
                new tools.redfox.bamboo.webhooks.listener.events.DeploymentStartedEvent(
                        results.getImmutablePlan().getName(),
                        getPlan(results),
                        getBuild(results),
                        version.getName(),
                        result.getEnvironment().getName()
                )
        );
    }

    @EventListener
    public void onDeploymentFinishedEvent(DeploymentFinishedEvent event) {
        @Nullable DeploymentResult result = deploymentResultService.getDeploymentResult(event.getDeploymentResultId());
        @Nullable DeploymentVersion version = result.getDeploymentVersion();
        PlanResultKey planKey = deploymentVersionService.getRelatedPlanResultKeys(version.getId()).stream().findFirst().get();
        @Nullable ResultsSummary results = resultsSummaryManager.getResultsSummary(planKey);

        handler.notify(
                new tools.redfox.bamboo.webhooks.listener.events.DeploymentFinishedEvent(
                        results.getImmutablePlan().getName(),
                        getPlan(results),
                        getBuild(results),
                        version.getName(),
                        result.getEnvironment().getName(),
                        result.getDeploymentState().toString().toUpperCase()
                )
        );
    }

    @EventListener
    public void onReleaseCreatedEvent(DeploymentVersionCreatedEvent event) {
        @Nullable DeploymentVersion version = deploymentVersionService.getDeploymentVersion(event.getVersionId());
        PlanResultKey planKey = deploymentVersionService.getRelatedPlanResultKeys(event.getVersionId()).stream().findFirst().get();
        @Nullable ResultsSummary results = resultsSummaryManager.getResultsSummary(planKey);

        handler.notify(
                new VersionCreatedEvent(
                        results.getImmutablePlan().getProject().getName(),
                        getPlan(results),
                        getBuild(results),
                        version.getName(),
                        version.getCreatorUserName()
                )
        );
    }

    protected Plan getPlan(ResultsSummary results) {
        return new Plan(
                results.getPlanName(),
                results.getPlanKey().getKey(),
                BuildEventFactory.getAbsoluteUrlFor("/browse/" + results.getPlanKey().getKey())
        );
    }

    protected Build getBuild(ResultsSummary results) {
        String reason = "Manual build";
        try {
            results.getTriggerReason().getName();
        } catch (Exception e) {
        }

        return new Build(
                results.getBuildResultKey(),
                results.getBuildNumber(),
                reason,
                false,
                results.isCustomBuild(),
                BuildEventFactory.getAbsoluteUrlFor("/browse/" + results.getBuildResultKey())
        ).setStatus("SUCCESS");
    }
}
