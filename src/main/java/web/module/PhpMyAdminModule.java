package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.Connector;
import web.Processor;
import web.Request;
import web.db.phpmyadmin.PhpMyAdminCheckVersionProcessor;
import web.db.phpmyadmin.PhpMyAdminVersionRequest;
import web.db.phpmyadmin.annotation.PhpMyAdmin;
import web.db.phpmyadmin.annotation.PhpMyAdminVersion;
import web.module.provider.PhpMyAdminProvider;

public class PhpMyAdminModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(PhpMyAdminVersion.class).to(PhpMyAdminVersionRequest.class);
        bind(Processor.class).annotatedWith(PhpMyAdminVersion.class).to(PhpMyAdminCheckVersionProcessor.class);

        bind(Connector.class).annotatedWith(PhpMyAdmin.class).toProvider(PhpMyAdminProvider.class);
    }

}
