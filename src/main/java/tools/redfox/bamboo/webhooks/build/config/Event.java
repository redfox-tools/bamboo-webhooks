package tools.redfox.bamboo.webhooks.build.config;

public class Event {
    private String name;
    private String value;

    public Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return "custom.bamboo.webhook.events." + name.toLowerCase().replaceAll("[^a-z]+", "_");
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Event && ((Event) obj).getId().equals(getId());
    }
}
