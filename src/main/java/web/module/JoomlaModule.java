package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import lombok.SneakyThrows;
import web.cms.joomla.*;
import web.cms.joomla.annotation.*;
import web.http.GetRequest;
import web.module.annotation.Get;
import web.module.provider.JoomlaProvider;
import web.parser.*;
import web.struct.*;
import web.http.Request;

public class JoomlaModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(Get.class).to(GetRequest.class);

        bind(Destination.class).to(SimpleDestination.class);

        bind(new TypeLiteral<TextParser<String>>(){}).to(StringReturnTextParser.class);
        bind(new TypeLiteral<XMLParser<String>>(){}).to(VersionXMLParser.class);

        bind(Source.class).annotatedWith(JoomlaPlugin.class).to(JoomlaExtensionSource.class);
        bind(Processor.class).annotatedWith(JoomlaPlugin.class).to(JoomlaPluginsProcessor.class);

        bind(Processor.class).annotatedWith(JoomlaVersion.class).to(JoomlaVersionProcessor.class);

        bind(Connector.class).annotatedWith(Joomla.class).toProvider(JoomlaProvider.class);
    }

}
