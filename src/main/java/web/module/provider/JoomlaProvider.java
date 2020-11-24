package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.joomla.JoomlaConnector;
import web.cms.joomla.annotation.JoomlaPlugin;
import web.cms.joomla.annotation.JoomlaVersion;
import web.struct.Connector;
import web.struct.Processor;

public class JoomlaProvider implements Provider<Connector> {

    private final Processor versionProcessor;
    private final Processor pluginProcessor;

    @Inject
    JoomlaProvider(@JoomlaVersion Processor versionProcessor,
                   @JoomlaPlugin Processor pluginProcessor) {
        this.versionProcessor = versionProcessor;
        this.pluginProcessor = pluginProcessor;
    }

    @Override
    public Connector get() {
        return new JoomlaConnector(versionProcessor, pluginProcessor);
    }

}
