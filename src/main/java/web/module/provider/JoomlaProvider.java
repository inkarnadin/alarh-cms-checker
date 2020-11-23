package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.joomla.JoomlaConnector;
import web.cms.joomla.annotation.JoomlaPlugin;
import web.struct.Connector;
import web.struct.Processor;

public class JoomlaProvider implements Provider<Connector> {

    private final Processor processor;

    @Inject
    JoomlaProvider(@JoomlaPlugin Processor processor) {
        this.processor = processor;
    }

    @Override
    public Connector get() {
        return new JoomlaConnector(processor);
    }

}
