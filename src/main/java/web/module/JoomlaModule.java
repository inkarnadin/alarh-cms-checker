package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.joomla.*;
import web.cms.joomla.annotation.*;
import web.module.provider.JoomlaProvider;
import web.struct.Connector;
import web.struct.Processor;
import web.struct.Request;
import web.struct.Source;

public class JoomlaModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(JoomlaPlugin.class).to(JoomlaComponentRequest.class);
        bind(Source.class).annotatedWith(JoomlaPlugin.class).to(JoomlaExtensionSource.class);
        bind(Processor.class).annotatedWith(JoomlaPlugin.class).to(JoomlaComponentProcessor.class);

        bind(Connector.class).annotatedWith(Joomla.class).toProvider(JoomlaProvider.class);
    }

}
