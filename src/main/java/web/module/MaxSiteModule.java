package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.maxsite.annotation.MaxSite;
import web.module.provider.MaxSiteProvider;
import web.struct.Connector;

public class MaxSiteModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Connector.class).annotatedWith(MaxSite.class).toProvider(MaxSiteProvider.class);
    }

}
