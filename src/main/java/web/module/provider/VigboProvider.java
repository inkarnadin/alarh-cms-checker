package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.vigbo.VigboConnector;
import web.struct.Connector;

public class VigboProvider implements Provider<Connector> {

    @Inject
    VigboProvider() {}

    @Override
    public Connector get() {
        return new VigboConnector();
    }

}
