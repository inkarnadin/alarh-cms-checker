package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.bitrix.annotation.Bitrix;
import web.module.provider.BitrixProvider;
import web.struct.Connector;

public class BitrixModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Connector.class).annotatedWith(Bitrix.class).toProvider(BitrixProvider.class);
    }

}
