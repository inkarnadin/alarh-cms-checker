package web.cms.classic.modx;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSVersionProcessor;
import web.http.Request;
import web.parser.TextParser;
import web.printer.Printer;
import web.struct.ResultContainer;

import java.util.regex.Pattern;

import static web.printer.PrinterMarker.VERSION_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class ModXVersionProcessor extends AbstractCMSVersionProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final ResultContainer resultContainer;
    @Named(VERSION_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, versionSet).prepare(host);
        versionAnalyzer.checkViaPageKeywords("manager", new Pattern[] {
                Pattern.compile("(Revolution|Evolution)")
        });

        assign(resultContainer, versionSet);
        printer.print(resultContainer);
    }

}
