package tools.redfox.bamboo.webhooks.listener.events;

import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Plan;

public class DeploymentStartedEvent extends AbstractEvent {
    private final String version;
    private final String environment;

    public DeploymentStartedEvent(String projectName, Plan plan, Build build, String version, String environment) {
        super(projectName, plan, build);
        this.version = version;
        this.environment = environment;
    }

    public String getVersion() {
        return version;
    }

    public String getEnvironment() {
        return environment;
    }
}
