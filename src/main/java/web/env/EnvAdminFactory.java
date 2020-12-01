package web.env;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.struct.Connector;
import web.module.PhpMyAdminModule;
import web.module.provider.PhpMyAdminProvider;

public class EnvAdminFactory {

    public static Connector getDBAdmin() {
        Injector injector = Guice.createInjector(
                new PhpMyAdminModule()
        );

        return injector.getInstance(PhpMyAdminProvider.class).get();
    }

}
