package web.db;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.Connector;
import web.module.PhpMyAdminModule;
import web.module.provider.PhpMyAdminProvider;

public class DBAdminFactory {

    public static Connector getDBAdmin() {
        Injector injector = Guice.createInjector(
                new PhpMyAdminModule()
        );

        return injector.getInstance(PhpMyAdminProvider.class).get();
    }

}
