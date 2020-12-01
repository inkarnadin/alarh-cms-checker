package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.vamshop.VamShopConnector;
import web.struct.Connector;

public class VamShopProvider implements Provider<Connector> {

    @Inject
    VamShopProvider() {}

    @Override
    public Connector get() {
        return new VamShopConnector();
    }

}
