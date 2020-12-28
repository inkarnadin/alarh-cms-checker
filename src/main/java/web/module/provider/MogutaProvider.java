package web.module.provider;

import com.google.inject.Provider;
import web.cms.moguta.MogutaConnector;
import web.struct.Connector;

public class MogutaProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new MogutaConnector();
    }

}
