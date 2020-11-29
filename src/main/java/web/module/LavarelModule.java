package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.lavarel.annotation.Lavarel;
import web.module.provider.LavarelProvider;
import web.struct.Connector;

public class LavarelModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Connector.class).annotatedWith(Lavarel.class).toProvider(LavarelProvider.class);
    }

}
