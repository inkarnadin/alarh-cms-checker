package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import lombok.SneakyThrows;
import web.http.Client;
import web.http.GetRequest;
import web.http.HttpClient;
import web.parser.StringReturnTextParser;
import web.parser.TextParser;
import web.struct.Connector;
import web.struct.Processor;
import web.http.Request;
import web.db.phpmyadmin.PhpMyAdminVersionProcessor;
import web.db.phpmyadmin.annotation.PhpMyAdmin;
import web.db.phpmyadmin.annotation.PhpMyAdminVersion;
import web.module.provider.PhpMyAdminProvider;

public class PhpMyAdminModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Client.class).to(HttpClient.class);
        bind(Request.class).to(GetRequest.class);
        bind(new TypeLiteral<TextParser<String>>(){}).to(StringReturnTextParser.class);

        bind(Processor.class).annotatedWith(PhpMyAdminVersion.class).to(PhpMyAdminVersionProcessor.class);

        bind(Connector.class).annotatedWith(PhpMyAdmin.class).toProvider(PhpMyAdminProvider.class);
    }

}
