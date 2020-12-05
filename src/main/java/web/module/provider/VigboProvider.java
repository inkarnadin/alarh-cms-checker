package web.module.provider;

import com.google.inject.Provider;
import web.cms.vigbo.VigboConnector;
import web.struct.Connector;

public class VigboProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new VigboConnector();
    }

}
