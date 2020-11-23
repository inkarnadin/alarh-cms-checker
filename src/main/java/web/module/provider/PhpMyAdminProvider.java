package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.struct.Connector;
import web.struct.Processor;
import web.db.phpmyadmin.PhpMyAdminConnector;
import web.db.phpmyadmin.annotation.PhpMyAdminVersion;

public class PhpMyAdminProvider implements Provider<Connector> {

    private final Processor processor;

    @Inject
    PhpMyAdminProvider(@PhpMyAdminVersion Processor processor) {
        this.processor = processor;
    }

    @Override
    public Connector get() {
        return new PhpMyAdminConnector(processor);
    }

}
