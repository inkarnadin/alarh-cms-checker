package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import lombok.SneakyThrows;
import web.env.EnvType;
import web.env.php.PhpVersionProcessor;
import web.env.php.annotation.Php;
import web.env.php.annotation.PhpVersion;
import web.env.webserver.WebServerProcessor;
import web.env.webserver.annotation.WebServer;
import web.http.Client;
import web.http.GetRequest;
import web.http.HttpClient;
import web.module.provider.PhpProvider;
import web.module.provider.WebServerProvider;
import web.parser.StringReturnTextParser;
import web.parser.TextParser;
import web.struct.Connector;
import web.struct.Destination;
import web.struct.Processor;
import web.http.Request;
import web.env.phpmyadmin.PhpMyAdminVersionProcessor;
import web.env.phpmyadmin.annotation.PhpMyAdmin;
import web.env.phpmyadmin.annotation.PhpMyAdminVersion;
import web.module.provider.PhpMyAdminProvider;
import web.struct.SimpleDestination;

public class EnvModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Client.class).to(HttpClient.class);
        bind(Request.class).to(GetRequest.class);
        bind(Destination.class).to(SimpleDestination.class).in(Scopes.NO_SCOPE);

        bind(new TypeLiteral<TextParser<String>>(){}).to(StringReturnTextParser.class);

        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(PhpMyAdminVersion.class).to(PhpMyAdminVersionProcessor.class);
        bind(Connector.class).annotatedWith(PhpMyAdmin.class).toProvider(PhpMyAdminProvider.class);

        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(PhpVersion.class).to(PhpVersionProcessor.class);
        bind(Connector.class).annotatedWith(Php.class).toProvider(PhpProvider.class);

        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(WebServer.class).to(WebServerProcessor.class);
        bind(Connector.class).annotatedWith(WebServer.class).toProvider(WebServerProvider.class);
    }

}
