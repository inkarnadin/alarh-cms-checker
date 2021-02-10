package web.cms.moguta;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSVersionProcessor;
import web.http.Request;
import web.parser.TextParser;
import web.printer.Printer;
import web.struct.Destination;

import java.util.regex.Pattern;

import static web.printer.PrinterMarker.VERSION_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class MogutaVersionProcessor extends AbstractCMSVersionProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;
    @Named(VERSION_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, versionSet).prepare(host);
        versionAnalyzer.checkViaMainPageMetaTag(new Pattern[] {
                Pattern.compile("<meta name=\"mogutacms\" content=\"(.*?)\"\\s?/>")
        });
        versionAnalyzer.checkViaPageKeywords("mg-admin", new Pattern[] {
                Pattern.compile("<!--.*VER v(.*)\\s-->")
        });

        assign(destination, versionSet);
        printer.print(destination);
    }

}
