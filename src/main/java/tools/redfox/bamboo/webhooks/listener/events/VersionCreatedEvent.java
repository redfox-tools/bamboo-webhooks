package tools.redfox.bamboo.webhooks.listener.events;

import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Plan;

public class VersionCreatedEvent extends AbstractEvent {
    private String name;
    private String createdBy;

    public VersionCreatedEvent(String projectName, Plan plan, Build build, String name, String createdBy) {
        super(projectName, plan, build);
        this.name = name;
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
