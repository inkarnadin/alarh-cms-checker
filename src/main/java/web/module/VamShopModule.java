package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.vamshop.annotation.VamShop;
import web.module.provider.VamShopProvider;
import web.struct.Connector;

public class VamShopModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Connector.class).annotatedWith(VamShop.class).toProvider(VamShopProvider.class);
    }

}
