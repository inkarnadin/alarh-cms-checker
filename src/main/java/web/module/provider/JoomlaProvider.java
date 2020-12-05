package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.CMSType;
import web.cms.joomla.JoomlaConnector;
import web.cms.joomla.annotation.JoomlaPlugin;
import web.cms.joomla.annotation.JoomlaVersion;
import web.struct.Connector;
import web.struct.Processor;

public class JoomlaProvider implements Provider<Connector> {

    @Inject @JoomlaVersion
    private Processor<CMSType> versionProcessor;
    @Inject @JoomlaPlugin
    private Processor<CMSType> pluginProcessor;

    @Override
    public Connector get() {
        return new JoomlaConnector(versionProcessor, pluginProcessor);
    }

}
