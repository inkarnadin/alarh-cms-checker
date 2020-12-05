package web.module.provider;

import com.google.inject.Provider;
import web.cms.vue.VueConnector;
import web.struct.Connector;

public class VueProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new VueConnector();
    }

}
