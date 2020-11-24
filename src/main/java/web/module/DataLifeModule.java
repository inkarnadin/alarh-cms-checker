package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.datalife.annotation.DataLife;
import web.module.provider.DataLifeProvider;
import web.struct.Connector;

public class DataLifeModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Connector.class).annotatedWith(DataLife.class).toProvider(DataLifeProvider.class);
    }

}
