package web.env;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.module.EnvModule;
import web.struct.Connector;

public class EnvironmentFactory {

    public static Connector getEnvironmentConnector(EnvType envType) {
        Injector injector = Guice.createInjector(new EnvModule());
        return injector.getInstance(envType.getConnector());
    }

}
