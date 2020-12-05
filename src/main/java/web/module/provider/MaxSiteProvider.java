package web.module.provider;

import com.google.inject.Provider;
import web.cms.maxsite.MaxSiteConnector;
import web.struct.Connector;

public class MaxSiteProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new MaxSiteConnector();
    }

}
