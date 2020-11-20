package web.cms;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.Connector;
import web.module.JoomlaModule;
import web.module.WordPressModule;
import web.module.provider.JoomlaProvider;
import web.module.provider.WordPressProvider;

public class CMSFactory {

    public static Connector getCMSConnector(String cmsType) {
        Injector injector = Guice.createInjector(
                new JoomlaModule(),
                new WordPressModule()
        );

        switch (CMSType.search(cmsType)) {
            case JOOMLA:
                 injector.getInstance(JoomlaProvider.class).get();
            case WORDPRESS:
                return injector.getInstance(WordPressProvider.class).get();
            default:
                throw new IllegalArgumentException("Unsupported CMS type");
        }
    }

}
