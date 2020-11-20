package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.*;
import web.cms.wordpress.WordPressCheckPluginProcessor;
import web.cms.wordpress.WordPressCheckPluginRequest;
import web.cms.wordpress.WordPressExtensionSource;
import web.cms.wordpress.annotation.WordPress;
import web.cms.wordpress.annotation.WordPressPlugin;
import web.module.provider.WordPressProvider;

public class WordPressModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(WordPressPlugin.class).to(WordPressCheckPluginRequest.class);
        bind(Source.class).annotatedWith(WordPressPlugin.class).to(WordPressExtensionSource.class);
        bind(Processor.class).annotatedWith(WordPressPlugin.class).to(WordPressCheckPluginProcessor.class);

        bind(Connector.class).annotatedWith(WordPress.class).toProvider(WordPressProvider.class);
    }

}
