package web.cms.react;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.analyzer.DissectorResult;
import web.analyzer.JsScriptDissector;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSVersionProcessor;
import web.http.Request;
import web.parser.TextParser;
import web.printer.Printer;
import web.struct.Destination;

import java.util.regex.Pattern;

import static web.printer.PrinterMarker.VERSION_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class ReactVersionProcessor extends AbstractCMSVersionProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;
    @Named(VERSION_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        DissectorResult dissectorResult = JsScriptDissector.dissect(host, request);
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, versionSet).prepare(host);
        versionAnalyzer.checkViaSinceScript(
                Pattern.compile("version:\"(.*?)\".*?SECRET_INTERNALS_DO_NOT_USE_OR_YOU_WILL_BE_FIRED"),
                dissectorResult.getPaths(),
                dissectorResult.isOverWrittenBasePath(),
                true
        );

        assign(destination, versionSet);
        printer.print(destination);
    }

}
