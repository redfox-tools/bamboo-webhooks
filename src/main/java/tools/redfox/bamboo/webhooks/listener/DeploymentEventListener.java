package tools.redfox.bamboo.webhooks.listener;

import com.atlassian.bamboo.deployments.execution.events.DeploymentFinishedEvent;
import com.atlassian.event.api.EventListener;

public class DeploymentEventListener {
    @EventListener
    public void onDeploymentEvent(DeploymentFinishedEvent event) {
        System.out.println("-------------------------> " + event);
    }
}
