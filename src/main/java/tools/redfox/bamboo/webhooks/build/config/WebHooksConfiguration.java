package tools.redfox.bamboo.webhooks.build.config;

import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.plan.TopLevelPlan;
import com.atlassian.bamboo.plan.cache.ImmutablePlan;
import com.atlassian.bamboo.plan.configuration.MiscellaneousPlanConfigurationPlugin;
import com.atlassian.bamboo.template.TemplateRenderer;
import com.atlassian.bamboo.v2.build.BaseBuildConfigurationAwarePlugin;
import com.atlassian.bamboo.ww2.actions.build.admin.create.BuildConfiguration;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class WebHooksConfiguration extends BaseBuildConfigurationAwarePlugin
        implements MiscellaneousPlanConfigurationPlugin {
    private Map<String, Event[]> buildEvents = new LinkedHashMap<String, Event[]>() {{
        put("Build", new Event[]{new Event("Build Started"), new Event("Build Finished")});
        put("Stage", new Event[]{new Event("Stage Started"), new Event("Stage Finished")});
        put("Job", new Event[]{new Event("Job Started"), new Event("Job Finished")});
        put("Deployment", new Event[]{new Event("Deployment Started"), new Event("Deployment Finished")});
        put("Release", new Event[]{new Event("Version Created")});
    }};

    public WebHooksConfiguration(@ComponentImport TemplateRenderer templateRenderer) {
        super();
        setTemplateRenderer(templateRenderer);
    }

    @Override
    public boolean isApplicableTo(@NotNull ImmutablePlan immutablePlan) {
        return immutablePlan instanceof TopLevelPlan;
    }

    @Override
    public boolean isApplicableTo(Plan plan) {
        return plan instanceof TopLevelPlan;
    }

    @Override
    protected void populateContextForEdit(@NotNull Map<String, Object> context,
                                          @NotNull BuildConfiguration buildConfiguration,
                                          Plan plan) {
        context.put("buildEvents", buildEvents.entrySet().iterator());
        context.put("mode", buildConfiguration.getInt("custom.bamboo.webhook.mode", 1));
    }
}
