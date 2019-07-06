package tools.redfox.bamboo.webhooks.listener.events.factory;

import com.atlassian.bamboo.configuration.AdministrationConfigurationAccessor;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.plugin.spring.scanner.annotation.component.BambooComponent;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tools.redfox.bamboo.webhooks.listener.events.BuildStartedEvent;

@BambooComponent
public class BuildEventFactory extends AbstractFactory {
    @Autowired
    public BuildEventFactory(AdministrationConfigurationAccessor administrationConfigurationAccessor) {
        super(administrationConfigurationAccessor);
    }

    public BuildStartedEvent createBuildStartedEvent(BuildContext buildContext) {
        BuildContext context = ObjectUtils.firstNonNull(buildContext.getParentBuildContext(), buildContext);
        return new BuildStartedEvent(
                buildContext.getProjectName(),
                getPlanFor(context),
                getBuildFor(context)
        );
    }
}
