package tools.redfox.bamboo.webhooks.listener.action;

import com.atlassian.bamboo.build.CustomPostBuildCompletedAction;
import com.atlassian.bamboo.v2.build.BuildContext;
import org.jetbrains.annotations.NotNull;

public class PostBuildAction implements CustomPostBuildCompletedAction {
    @Override
    public void init(@NotNull BuildContext buildContext) {

    }

    @Override
    public @NotNull BuildContext call() throws InterruptedException, Exception {
        return null;
    }
}
