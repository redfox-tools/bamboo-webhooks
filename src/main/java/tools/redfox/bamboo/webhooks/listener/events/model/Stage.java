package tools.redfox.bamboo.webhooks.listener.events.model;

import com.atlassian.bamboo.chains.StageExecution;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.LinkedList;
import java.util.List;

public class Stage {
    private final String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Job job;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Job> jobs;

    public Stage(String name) {
        this.name = name;
    }

    public Stage(StageExecution execution) {
        this(execution.getName());
    }

    public Stage(StageExecution stageExecution, Job job) {
        this(stageExecution);
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public Job getJob() {
        return job;
    }

    public Stage setJob(Job job) {
        this.job = job;
        return this;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public void addJob(Job job) {
        if (this.jobs == null) {
            this.jobs = new LinkedList<>();
        }
        this.jobs.add(job);
    }
}
