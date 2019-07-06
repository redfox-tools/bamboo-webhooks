package tools.redfox.bamboo.webhooks.listener.action;

import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.bamboo.ww2.actions.build.admin.create.BuildConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBuildAction implements com.atlassian.bamboo.build.CustomPreBuildAction {
    protected BuildContext buildContext;

    @Override
    public @Nullable ErrorCollection validate(BuildConfiguration buildConfiguration) {
        return null;
    }

    @Override
    public void init(@NotNull BuildContext buildContext) {
        this.buildContext = buildContext;
    }

    @Override
    public @NotNull BuildContext call() throws InterruptedException, Exception {
        return buildContext;
    }
}
