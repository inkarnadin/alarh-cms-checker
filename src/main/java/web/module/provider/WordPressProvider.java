package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.Connector;
import web.Processor;
import web.cms.wordpress.WordPressConnector;
import web.cms.wordpress.annotation.WordPressPlugin;

public class WordPressProvider implements Provider<Connector> {

    private final Processor processor;

    @Inject
    WordPressProvider(@WordPressPlugin Processor processor) {
        this.processor = processor;
    }

    @Override
    public Connector get() {
        return new WordPressConnector(processor);
    }

}
