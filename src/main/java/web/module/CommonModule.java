package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import web.http.Client;
import web.http.GetRequest;
import web.http.HttpClient;
import web.http.Request;
import web.parser.*;
import web.printer.CheckPrinter;
import web.printer.ListPrinter;
import web.printer.Printer;
import web.printer.VersionPrinter;
import web.struct.Destination;
import web.struct.SimpleDestination;

import static web.printer.PrinterMarker.*;

public class CommonModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Client.class).to(HttpClient.class).asEagerSingleton();
        bind(Request.class).to(GetRequest.class);
        bind(Destination.class).to(SimpleDestination.class).in(Scopes.NO_SCOPE);

        bind(new TypeLiteral<TextParser<Boolean>>(){}).to(BooleanReturnTextParser.class);
        bind(new TypeLiteral<TextParser<String>>(){}).to(StringReturnTextParser.class);
        bind(new TypeLiteral<XMLParser<String>>(){}).to(VersionXMLParser.class);

        bind(Printer.class).annotatedWith(Names.named(LIST_PRINTER)).to(ListPrinter.class);
        bind(Printer.class).annotatedWith(Names.named(CHECK_PRINTER)).to(CheckPrinter.class);
        bind(Printer.class).annotatedWith(Names.named(VERSION_PRINTER)).to(VersionPrinter.class);
    }

}
