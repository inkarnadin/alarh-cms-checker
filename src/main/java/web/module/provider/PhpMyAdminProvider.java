package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.env.EnvType;
import web.struct.Connector;
import web.struct.Processor;
import web.env.phpmyadmin.PhpMyAdminConnector;
import web.env.phpmyadmin.annotation.PhpMyAdminVersion;

public class PhpMyAdminProvider implements Provider<Connector> {

    @Inject @PhpMyAdminVersion
    private Processor<EnvType> processor;

    @Override
    public Connector get() {
        return new PhpMyAdminConnector(processor);
    }

}
