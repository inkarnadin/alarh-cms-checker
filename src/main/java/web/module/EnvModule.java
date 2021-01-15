package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import lombok.SneakyThrows;
import web.env.EnvType;
import web.env.whois.WhoisCheckProcessor;
import web.env.php.PhpProcessor;
import web.env.webserver.WebServerProcessor;
import web.http.Client;
import web.http.GetRequest;
import web.http.HttpClient;
import web.parser.StringReturnTextParser;
import web.parser.TextParser;
import web.struct.Destination;
import web.struct.Processor;
import web.http.Request;
import web.env.phpmyadmin.PhpMyAdminProcessor;
import web.struct.SimpleDestination;

import static web.env.EnvMarker.*;

public class EnvModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Client.class).to(HttpClient.class);
        bind(Request.class).to(GetRequest.class);
        bind(Destination.class).to(SimpleDestination.class).in(Scopes.NO_SCOPE);

        bind(new TypeLiteral<TextParser<String>>(){}).to(StringReturnTextParser.class);

        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(Names.named(PHP_MY_ADMIN)).to(PhpMyAdminProcessor.class);
        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(Names.named(PHP)).to(PhpProcessor.class);
        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(Names.named(WEB_SERVER)).to(WebServerProcessor.class);
        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(Names.named(WHOIS)).to(WhoisCheckProcessor.class);
    }

}
