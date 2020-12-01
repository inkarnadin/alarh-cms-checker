package web.env;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.module.provider.PhpProvider;
import web.struct.Connector;
import web.module.EnvModule;
import web.module.provider.PhpMyAdminProvider;

public class EnvironmentFactory {

    public static Connector getDBAdmin(EnvType envType) {
        Injector injector = Guice.createInjector(
                new EnvModule()
        );

        switch (envType) {
            case PHP_MY_ADMIN:
                return injector.getInstance(PhpMyAdminProvider.class).get();
            case PHP:
                return injector.getInstance(PhpProvider.class).get();
            default:
                throw new IllegalArgumentException("Unknown environment type");
        }
    }

}
