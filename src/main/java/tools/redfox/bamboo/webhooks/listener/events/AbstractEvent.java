package tools.redfox.bamboo.webhooks.listener.events;

import tools.redfox.bamboo.webhooks.listener.events.model.Build;
import tools.redfox.bamboo.webhooks.listener.events.model.Plan;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class AbstractEvent {
    private String id = UUID.randomUUID().toString();
    private String time = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

    private final Plan plan;
    private Build build;
    private final String projectName;

    public AbstractEvent(String projectName, Plan plan, Build build) {
        this.projectName = projectName;
        this.plan = plan;
        this.build = build;
    }

    public Plan getPlan() {
        return plan;
    }

    public String getProjectName() {
        return projectName;
    }

    public Build getBuild() {
        return build;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getConfigKey() {
        return "custom.bamboo.webhook.events." + getEventName().toLowerCase().replaceAll("[^a-z]+", "_");
    }

    protected abstract String getEventName();
}
