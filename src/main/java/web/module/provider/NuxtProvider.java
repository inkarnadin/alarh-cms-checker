package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.nuxt.NuxtConnector;
import web.struct.Connector;

public class NuxtProvider implements Provider<Connector> {

    @Inject
    NuxtProvider() {}

    @Override
    public Connector get() {
        return new NuxtConnector();
    }

}
