package web.cms;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.Connector;
import web.module.CmsPluginModule;
import web.module.JoomlaProvider;
import web.module.WordPressProvider;
import web.cms.CMSType;

public class CMSFactory {

    public static Connector getCMSConnector(String cmsType) {
        Injector injector = Guice.createInjector(new CmsPluginModule());

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
