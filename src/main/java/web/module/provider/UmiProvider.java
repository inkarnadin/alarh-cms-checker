package web.module.provider;

import com.google.inject.Provider;
import web.cms.umi.UmiConnector;
import web.struct.Connector;

public class UmiProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new UmiConnector();
    }

}
