package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.opencart.OpenCartConnector;
import web.struct.Connector;

public class OpenCartProvider implements Provider<Connector> {

    @Inject
    OpenCartProvider() {}

    @Override
    public Connector get() {
        return new OpenCartConnector();
    }

}
