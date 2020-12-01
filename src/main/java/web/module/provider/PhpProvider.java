package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.env.php.PhpConnector;
import web.env.php.annotation.PhpVersion;
import web.env.phpmyadmin.PhpMyAdminConnector;
import web.env.phpmyadmin.annotation.PhpMyAdminVersion;
import web.struct.Connector;
import web.struct.Processor;

public class PhpProvider implements Provider<Connector> {

    private final Processor processor;

    @Inject
    PhpProvider(@PhpVersion Processor processor) {
        this.processor = processor;
    }

    @Override
    public Connector get() {
        return new PhpConnector(processor);
    }

}
