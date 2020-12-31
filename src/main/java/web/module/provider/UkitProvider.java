package web.module.provider;

import com.google.inject.Provider;
import web.cms.ukit.UkitConnector;
import web.struct.Connector;

public class UkitProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new UkitConnector();
    }

}
