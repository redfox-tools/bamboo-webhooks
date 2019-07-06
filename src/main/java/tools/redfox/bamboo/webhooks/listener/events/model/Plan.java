package tools.redfox.bamboo.webhooks.listener.events.model;

public class Plan {
    private final String planName;
    private final String key;
    private final String url;

    public Plan(String planName, String planKey, String url) {
        this.planName = planName;
        this.key = planKey;
        this.url = url;
    }

    public String getPlanName() {
        return planName;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }
}
