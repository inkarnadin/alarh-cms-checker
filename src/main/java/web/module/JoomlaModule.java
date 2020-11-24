package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import lombok.SneakyThrows;
import web.cms.joomla.*;
import web.cms.joomla.annotation.*;
import web.cms.joomla.parser.JoomlaVersionXMLParser;
import web.cms.joomla.parser.JoomlaVersionPublicMetaInfoParser;
import web.http.GetRequest;
import web.module.annotation.Get;
import web.module.provider.JoomlaProvider;
import web.struct.Parser;
import web.struct.*;
import web.http.Request;

public class JoomlaModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(Get.class).to(GetRequest.class);

        bind(Source.class).annotatedWith(JoomlaPlugin.class).to(JoomlaExtensionSource.class);
        bind(Processor.class).annotatedWith(JoomlaPlugin.class).to(JoomlaComponentProcessor.class);

        bind(Parser.class).annotatedWith(Names.named("ViaLangPackage")).to(JoomlaVersionXMLParser.class);
        bind(Parser.class).annotatedWith(Names.named("ViaPublicMetaInf")).to(JoomlaVersionPublicMetaInfoParser.class);
        bind(Destination.class).annotatedWith(JoomlaVersion.class).to(SimpleDestination.class);
        bind(Processor.class).annotatedWith(JoomlaVersion.class).to(JoomlaVersionProcessor.class);

        bind(Connector.class).annotatedWith(Joomla.class).toProvider(JoomlaProvider.class);
    }

}
