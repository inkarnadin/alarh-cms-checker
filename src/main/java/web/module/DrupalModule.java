package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import lombok.SneakyThrows;
import web.cms.drupal.DrupalVersionProcessor;
import web.cms.drupal.annotation.Drupal;
import web.cms.drupal.annotation.DrupalVersion;
import web.http.GetRequest;
import web.http.Request;
import web.module.annotation.Get;
import web.module.provider.DrupalProvider;
import web.parser.StringReturnTextParser;
import web.parser.TextParser;
import web.struct.Connector;
import web.struct.Destination;
import web.struct.Processor;
import web.struct.SimpleDestination;

public class DrupalModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(Get.class).to(GetRequest.class);
        bind(Destination.class).to(SimpleDestination.class);
        bind(new TypeLiteral<TextParser<String>>(){}).to(StringReturnTextParser.class);
        bind(Processor.class).annotatedWith(DrupalVersion.class).to(DrupalVersionProcessor.class);
        bind(Connector.class).annotatedWith(Drupal.class).toProvider(DrupalProvider.class);
    }

}
