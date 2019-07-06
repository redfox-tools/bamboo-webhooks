package tools.redfox.bamboo.webhooks.listener.events.model;

import com.atlassian.bamboo.chains.ChainResultsSummary;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.bamboo.v2.build.BuildIdentifier;
import com.atlassian.bamboo.v2.build.trigger.TriggerReason;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.LinkedList;
import java.util.List;

public class Build {
    private final String key;
    private final int number;
    private final String trigger;
    private final boolean isBranchBuild;
    private final boolean isCustomBuild;
    private final String url;

    private String status = "IN PROGRESS";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Stage stage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String summary;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Stage> stages;


    public Build(String key, int number, String trigger, boolean isBranchBuild, boolean isCustomBuild, String url) {
        this.key = key;
        this.number = number;
        this.trigger = trigger;
        this.isBranchBuild = isBranchBuild;
        this.isCustomBuild = isCustomBuild;
        this.url = url;
    }

    public Build(BuildContext buildContext, String url) {
        this(
                buildContext.getBuildResultKey(),
                buildContext.getBuildNumber(),
                buildContext.getTriggerReason().getName(),
                buildContext.isBranch(),
                buildContext.isCustomBuild(),
                url
        );
    }

    public Build(ChainResultsSummary summary, String url) {
        this(
                summary.getBuildResultKey(),
                summary.getBuildNumber(),
                summary.getTriggerReason().getName(),
                false, // is there a better way?
                summary.isCustomBuild(),
                url
        );
    }

    public Build(BuildContext buildContext, Stage stage, String baseUrl) {
        this(buildContext, baseUrl);
        this.stage = stage;
    }

    public String getKey() {
        return key;
    }

    public String getTrigger() {
        return trigger;
    }

    public boolean isBranchBuild() {
        return isBranchBuild;
    }

    public boolean isCustomBuild() {
        return isCustomBuild;
    }

    public String getUrl() {
        return url;
    }

    public Stage getStage() {
        return stage;
    }

    public int getNumber() {
        return number;
    }

    public Build setStage(Stage stage) {
        this.stage = stage;
        return this;
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

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public void addStage(Stage stages) {
        if (this.stages == null) {
            this.stages = new LinkedList<>();
        }

        this.stages.add(stages);
    }
}
