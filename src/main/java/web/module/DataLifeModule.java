package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.datalife.DataLifeVersionProcessor;
import web.cms.datalife.annotation.DataLife;
import web.cms.datalife.annotation.DataLifeVersion;
import web.module.provider.DataLifeProvider;
import web.struct.Connector;
import web.struct.Processor;

public class DataLifeModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Processor.class).annotatedWith(DataLifeVersion.class).to(DataLifeVersionProcessor.class);
        bind(Connector.class).annotatedWith(DataLife.class).toProvider(DataLifeProvider.class);
    }

}
