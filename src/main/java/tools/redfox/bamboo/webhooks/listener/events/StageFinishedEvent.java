package tools.redfox.bamboo.webhooks.listener.events;

import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Plan;

public class StageFinishedEvent extends AbstractEvent {
    public StageFinishedEvent(String projectName, Plan plan, Build build) {
        super(projectName, plan, build);
    }
}
