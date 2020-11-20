package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.*;
import web.cms.joomla.*;
import web.cms.joomla.annotation.*;
import web.cms.wordpress.WordPressCheckPluginProcessor;
import web.cms.wordpress.WordPressCheckPluginRequest;
import web.cms.wordpress.WordPressExtensionSource;
import web.cms.wordpress.annotation.*;

public class CmsPluginModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(JoomlaPlugin.class).to(JoomlaCheckComponentRequest.class);
        bind(Source.class).annotatedWith(JoomlaPlugin.class).to(JoomlaExtensionSource.class);
        bind(PluginProcessor.class).annotatedWith(JoomlaPlugin.class).to(JoomlaCheckComponentProcessor.class);

        bind(Connector.class).annotatedWith(Joomla.class).toProvider(JoomlaProvider.class);

        bind(Request.class).annotatedWith(WordPressPlugin.class).to(WordPressCheckPluginRequest.class);
        bind(Source.class).annotatedWith(WordPressPlugin.class).to(WordPressExtensionSource.class);
        bind(PluginProcessor.class).annotatedWith(WordPressPlugin.class).to(WordPressCheckPluginProcessor.class);

        bind(Connector.class).annotatedWith(WordPress.class).toProvider(WordPressProvider.class);
    }


}
