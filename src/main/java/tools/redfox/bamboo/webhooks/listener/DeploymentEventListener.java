package tools.redfox.bamboo.webhooks.listener;

import com.atlassian.bamboo.deployments.execution.events.DeploymentFinishedEvent;
import com.atlassian.bamboo.deployments.execution.events.DeploymentStartedEvent;
import com.atlassian.bamboo.deployments.versions.DeploymentVersion;
import com.atlassian.bamboo.deployments.versions.events.DeploymentVersionCreatedEvent;
import com.atlassian.bamboo.deployments.versions.service.DeploymentVersionService;
import com.atlassian.bamboo.plan.PlanManager;
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
    private DeploymentVersionService deploymentVersionService;
    private ResultsSummaryManager resultsSummaryManager;
    private PlanManager planManager;
    private WebhookHandler handler;

    @Autowired
    public DeploymentEventListener(
            DeploymentVersionService deploymentVersionService,
            @ComponentImport ResultsSummaryManager resultsSummaryManager,
            @ComponentImport PlanManager planManager,
            WebhookHandler handler
    ) {
        this.deploymentVersionService = deploymentVersionService;
        this.resultsSummaryManager = resultsSummaryManager;
        this.planManager = planManager;
        this.handler = handler;
    }

    @EventListener
    public void onDeploymentStarted(DeploymentStartedEvent event) {
        System.out.println("-------------------------> deployment started <----------------------------------" + event);
    }

    @EventListener
    public void onDeploymentFinishedEvent(DeploymentFinishedEvent event) {
        System.out.println("-------------------------> deployment finished <----------------------------------" + event);
    }

    @EventListener
    public void onReleaseCreatedEvent(DeploymentVersionCreatedEvent event) {
        @Nullable DeploymentVersion version = deploymentVersionService.getDeploymentVersion(event.getVersionId());
        PlanResultKey planKey = deploymentVersionService.getRelatedPlanResultKeys(event.getVersionId()).stream().findFirst().get();
        @Nullable ResultsSummary results = resultsSummaryManager.getResultsSummary(planKey);

        handler.notify(
                new VersionCreatedEvent(
                        results.getImmutablePlan().getProject().getName(),
                        new Plan(
                                results.getPlanName(),
                                results.getPlanResultKey().getKey(),
                                BuildEventFactory.getAbsoluteUrlFor("/browse/" + results.getPlanResultKey().getKey())
                        ),
                        new Build(
                                results.getBuildResultKey(),
                                results.getBuildNumber(),
                                results.getTriggerReason().getName(),
                                false,
                                results.isCustomBuild(),
                                BuildEventFactory.getAbsoluteUrlFor("/browse/" + results.getBuildResultKey())
                        ).setStatus("SUCCESS"),
                        version.getName(),
                        version.getCreatorUserName()
                ),
                null
        );
    }
}
