package web.env.webserver;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import web.analyzer.version.VersionAnalyzer;
import web.env.AbstractEnvironmentProcessor;
import web.env.EnvType;
import web.http.Request;
import web.parser.TextParser;
import web.printer.Printer;
import web.struct.Destination;

import java.util.regex.Pattern;

import static web.printer.PrinterMarker.LIST_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WebServerProcessor extends AbstractEnvironmentProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;
    @Named(LIST_PRINTER)
    private final Printer printer;

    @Override
    @SneakyThrows
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, versionSet).prepare(host);
        versionAnalyzer.checkViaHeaders(Pattern.compile("(.*)"), "server");

        assign(destination, EnvType.WEB_SERVER, versionSet);
        printer.print(destination);
    }

}
