package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.Connector;
import web.PluginProcessor;
import web.cms.wordpress.WordPressConnector;
import web.cms.wordpress.annotation.WordPressPlugin;

public class WordPressProvider implements Provider<Connector> {

    private final PluginProcessor pluginProcessor;

    @Inject
    WordPressProvider(@WordPressPlugin PluginProcessor pluginProcessor) {
        this.pluginProcessor = pluginProcessor;
    }

    @Override
    public Connector get() {
        return new WordPressConnector(pluginProcessor);
    }

}
