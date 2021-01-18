package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import lombok.SneakyThrows;
import web.env.EnvType;
import web.env.whois.WhoisCheckProcessor;
import web.env.php.PhpCheckProcessor;
import web.env.webserver.WebServerProcessor;
import web.http.Client;
import web.http.GetRequest;
import web.http.HttpClient;
import web.parser.StringReturnTextParser;
import web.parser.TextParser;
import web.printer.Printer;
import web.printer.CommonPrinter;
import web.struct.Destination;
import web.struct.Processor;
import web.http.Request;
import web.env.phpmyadmin.PhpMyAdminCheckProcessor;
import web.struct.SimpleDestination;

import static web.env.EnvMarker.*;

public class EnvModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        install(new CommonModule());

        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(Names.named(PHP_MY_ADMIN)).to(PhpMyAdminCheckProcessor.class);
        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(Names.named(PHP)).to(PhpCheckProcessor.class);
        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(Names.named(WEB_SERVER)).to(WebServerProcessor.class);
        bind(new TypeLiteral<Processor<EnvType>>(){}).annotatedWith(Names.named(WHOIS)).to(WhoisCheckProcessor.class);
    }

}
