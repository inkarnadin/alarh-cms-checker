package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.env.EnvType;
import web.env.php.PhpConnector;
import web.env.php.annotation.PhpVersion;
import web.struct.Connector;
import web.struct.Processor;

public class PhpProvider implements Provider<Connector> {

    @Inject @PhpVersion
    private Processor<EnvType> processor;

    @Override
    public Connector get() {
        return new PhpConnector(processor);
    }

}
