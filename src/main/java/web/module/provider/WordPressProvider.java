package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.CMSType;
import web.cms.wordpress.annotation.WordPressVersion;
import web.struct.Connector;
import web.struct.Processor;
import web.cms.wordpress.WordPressConnector;
import web.cms.wordpress.annotation.WordPressPlugin;

public class WordPressProvider implements Provider<Connector> {

    @Inject @WordPressVersion
    private Processor<CMSType> versionProcessor;
    @Inject @WordPressPlugin
    private Processor<CMSType> pluginProcessor;

    @Override
    public Connector get() {
        return new WordPressConnector(versionProcessor, pluginProcessor);
    }

}
