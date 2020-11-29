package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.lavarel.LavarelConnector;
import web.struct.Connector;

public class LavarelProvider implements Provider<Connector> {

    @Inject
    LavarelProvider() {}

    @Override
    public Connector get() {
        return new LavarelConnector();
    }

}
