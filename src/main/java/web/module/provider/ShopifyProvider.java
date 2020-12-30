package web.module.provider;

import com.google.inject.Provider;
import web.cms.shopify.ShopifyConnector;
import web.struct.Connector;

public class ShopifyProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new ShopifyConnector();
    }

}
