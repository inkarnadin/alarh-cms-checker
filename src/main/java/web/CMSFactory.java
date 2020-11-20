package web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.module.CmsPluginModule;
import web.module.JoomlaProvider;
import web.module.WordPressProvider;
import web.plugin.CmsType;

public class CMSFactory {

    public static Connector getCMSConnector(String cmsType) {
        Injector injector = Guice.createInjector(new CmsPluginModule());

        switch (CmsType.search(cmsType)) {
            case JOOMLA:
                 injector.getInstance(JoomlaProvider.class).get();
            case WORDPRESS:
                return injector.getInstance(WordPressProvider.class).get();
            default:
                throw new IllegalArgumentException("Unsupported CMS type");
        }
    }

}
