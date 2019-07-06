package tools.redfox.bamboo.webhooks.listener.events;

import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Plan;

public class StageStartedEvent extends AbstractEvent {
    public StageStartedEvent(String projectName, Plan plan, Build build) {
        super(projectName, plan, build);
    }
}
