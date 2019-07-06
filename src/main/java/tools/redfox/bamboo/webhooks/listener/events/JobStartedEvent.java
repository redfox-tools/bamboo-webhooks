package tools.redfox.bamboo.webhooks.listener.events;

import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Plan;

public class JobStartedEvent extends AbstractEvent {
    public JobStartedEvent(String projectName, Plan plan, Build build) {
        super(projectName, plan, build);
    }

    @Override
    protected String getEventName() {
        return "Job Started";
    }
}
