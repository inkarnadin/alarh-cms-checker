package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.magento.MagentoConnector;
import web.struct.Connector;

public class MagentoProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new MagentoConnector();
    }

}
