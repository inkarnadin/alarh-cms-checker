package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.tilda.annotation.Tilda;
import web.module.provider.TildaProvider;
import web.struct.Connector;

public class TildaModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Connector.class).annotatedWith(Tilda.class).toProvider(TildaProvider.class);
    }

}
