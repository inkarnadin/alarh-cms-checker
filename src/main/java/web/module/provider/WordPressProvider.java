package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.wordpress.annotation.WordPressVersion;
import web.struct.Connector;
import web.struct.Processor;
import web.cms.wordpress.WordPressConnector;
import web.cms.wordpress.annotation.WordPressPlugin;

public class WordPressProvider implements Provider<Connector> {

    private final Processor versionProcessor;
    private final Processor pluginProcessor;

    @Inject
    WordPressProvider(@WordPressVersion Processor versionProcessor,
                      @WordPressPlugin Processor pluginProcessor) {
        this.versionProcessor = versionProcessor;
        this.pluginProcessor = pluginProcessor;
    }

    @Override
    public Connector get() {
        return new WordPressConnector(versionProcessor, pluginProcessor);
    }

}
