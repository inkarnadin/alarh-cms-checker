package web.cms;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.module.*;
import web.module.provider.*;
import web.struct.Connector;

public class CMSFactory {

    public static Connector getCMSConnector(CMSType cmsType) {
        Injector injector = Guice.createInjector(
                new JoomlaModule(),
                new WordPressModule(),
                new YiiModule(),
                new DataLifeModule(),
                new MaxSiteModule(),
                new DrupalModule(),
                new BitrixModule(),
                new ModXModule(),
                new LavarelModule()
        );

        switch (cmsType) {
            case JOOMLA:
                return injector.getInstance(JoomlaProvider.class).get();
            case WORDPRESS:
                return injector.getInstance(WordPressProvider.class).get();
            case YII:
                return injector.getInstance(YiiProvider.class).get();
            case DATALIFE_ENGINE:
                return injector.getInstance(DataLifeProvider.class).get();
            case MAXSITE_CMS:
                return injector.getInstance(MaxSiteProvider.class).get();
            case DRUPAL:
                return injector.getInstance(DrupalProvider.class).get();
            case BITRIX:
                return injector.getInstance(BitrixProvider.class).get();
            case MODX:
                return injector.getInstance(ModXProvider.class).get();
            case LAVAREL:
                return injector.getInstance(LavarelProvider.class).get();
            default:
                throw new IllegalArgumentException("Unsupported CMS type");
        }
    }

}
