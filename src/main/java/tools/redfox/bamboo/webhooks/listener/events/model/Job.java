package tools.redfox.bamboo.webhooks.listener.events.model;

import com.atlassian.bamboo.v2.build.BuildContext;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Job {
    private final String name;
    private final String url;
    private String status = "STARTED";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String summary;

    public Job(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Job(BuildContext buildContext, String url) {
        this(buildContext.getShortName(), url);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
