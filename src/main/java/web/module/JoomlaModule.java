package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.*;
import web.cms.joomla.*;
import web.cms.joomla.annotation.*;
import web.module.provider.JoomlaProvider;

public class JoomlaModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(JoomlaPlugin.class).to(JoomlaCheckComponentRequest.class);
        bind(Source.class).annotatedWith(JoomlaPlugin.class).to(JoomlaExtensionSource.class);
        bind(Processor.class).annotatedWith(JoomlaPlugin.class).to(JoomlaCheckComponentProcessor.class);

        bind(Connector.class).annotatedWith(Joomla.class).toProvider(JoomlaProvider.class);
    }

}
