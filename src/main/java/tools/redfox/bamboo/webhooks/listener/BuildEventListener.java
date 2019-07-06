package tools.redfox.bamboo.webhooks.listener;

import com.atlassian.bamboo.event.ChainEvent;
import com.atlassian.event.api.EventListener;

public class BuildEventListener {
    @EventListener
    public void onBuildEvent(ChainEvent event) {
        System.out.println("========================> " + event);
    }
}
