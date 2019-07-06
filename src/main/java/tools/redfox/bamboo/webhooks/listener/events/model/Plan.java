package tools.redfox.bamboo.webhooks.listener.events.model;

public class Plan {
    private final String name;
    private final String key;
    private final String url;

    public Plan(String name, String planKey, String url) {
        this.name = name;
        this.key = planKey;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }
}
