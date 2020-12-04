package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.rails.RubyOnRailsConnector;
import web.struct.Connector;

public class RubyOnRailsProvider implements Provider<Connector> {

    @Inject
    RubyOnRailsProvider() {}

    @Override
    public Connector get() {
        return new RubyOnRailsConnector();
    }

}
