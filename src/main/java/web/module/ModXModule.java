package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.modx.annotation.ModX;
import web.module.provider.ModXProvider;
import web.struct.Connector;

public class ModXModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Connector.class).annotatedWith(ModX.class).toProvider(ModXProvider.class);
    }

}
