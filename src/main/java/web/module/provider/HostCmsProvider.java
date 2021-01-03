package web.module.provider;

import com.google.inject.Provider;
import web.cms.host.HostCmsConnector;
import web.struct.Connector;

public class HostCmsProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new HostCmsConnector();
    }

}
