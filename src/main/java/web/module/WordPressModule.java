package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import lombok.SneakyThrows;
import web.cms.wordpress.WordPressPluginProcessor;
import web.cms.wordpress.WordPressExtensionSource;
import web.cms.wordpress.WordPressVersionProcessor;
import web.cms.wordpress.annotation.WordPress;
import web.cms.wordpress.annotation.WordPressPlugin;
import web.cms.wordpress.annotation.WordPressVersion;
import web.http.GetRequest;
import web.module.annotation.Get;
import web.module.provider.WordPressProvider;
import web.parser.StringReturnTextParser;
import web.parser.TextParser;
import web.struct.*;
import web.http.Request;

public class WordPressModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(Get.class).to(GetRequest.class);

        bind(Destination.class).to(SimpleDestination.class);

        bind(new TypeLiteral<TextParser<String>>(){}).to(StringReturnTextParser.class);

        bind(Source.class).annotatedWith(WordPressPlugin.class).to(WordPressExtensionSource.class);
        bind(Processor.class).annotatedWith(WordPressPlugin.class).to(WordPressPluginProcessor.class);

        bind(Processor.class).annotatedWith(WordPressVersion.class).to(WordPressVersionProcessor.class);

        bind(Connector.class).annotatedWith(WordPress.class).toProvider(WordPressProvider.class);
    }

}
