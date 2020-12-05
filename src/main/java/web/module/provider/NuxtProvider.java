package web.module.provider;

import com.google.inject.Provider;
import web.cms.nuxt.NuxtConnector;
import web.struct.Connector;

public class NuxtProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new NuxtConnector();
    }

}
