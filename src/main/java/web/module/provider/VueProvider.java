package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.vue.VueConnector;
import web.struct.Connector;

public class VueProvider implements Provider<Connector> {

    @Inject
    VueProvider() {}

    @Override
    public Connector get() {
        return new VueConnector();
    }

}
