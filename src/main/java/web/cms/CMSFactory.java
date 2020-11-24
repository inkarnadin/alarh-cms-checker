package web.cms;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.module.provider.YiiProvider;
import web.struct.Connector;
import web.module.JoomlaModule;
import web.module.WordPressModule;
import web.module.provider.JoomlaProvider;
import web.module.provider.WordPressProvider;

public class CMSFactory {

    public static Connector getCMSConnector(CMSType cmsType) {
        Injector injector = Guice.createInjector(
                new JoomlaModule(),
                new WordPressModule()
        );

        switch (cmsType) {
            case JOOMLA:
                return injector.getInstance(JoomlaProvider.class).get();
            case WORDPRESS:
                return injector.getInstance(WordPressProvider.class).get();
            case YII:
                return injector.getInstance(YiiProvider.class).get();
            default:
                throw new IllegalArgumentException("Unsupported CMS type");
        }
    }

}
