package tools.redfox.bamboo.webhooks.listener;

import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.event.events.PluginDisablingEvent;
import com.atlassian.plugin.event.events.PluginEnabledEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import kong.unirest.JacksonObjectMapper;
import kong.unirest.Unirest;

public class PluginEventListener {
    @EventListener
    public void onPluginEnabled(PluginEnabledEvent event) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Unirest.config().setObjectMapper(new JacksonObjectMapper(mapper));
    }

    @EventListener
    public void onPluginDisabling(PluginDisablingEvent event) {
        Unirest.shutDown();
    }
}
