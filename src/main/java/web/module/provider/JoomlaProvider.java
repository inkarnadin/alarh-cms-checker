package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.*;
import web.cms.joomla.JoomlaConnector;
import web.cms.joomla.annotation.JoomlaPlugin;

public class JoomlaProvider implements Provider<Connector> {

    private final PluginProcessor pluginProcessor;

    @Inject
    JoomlaProvider(@JoomlaPlugin PluginProcessor pluginProcessor) {
        this.pluginProcessor = pluginProcessor;
    }

    @Override
    public Connector get() {
        return new JoomlaConnector(pluginProcessor);
    }

}
