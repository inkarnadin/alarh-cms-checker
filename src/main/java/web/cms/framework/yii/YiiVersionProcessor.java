package web.cms.framework.yii;

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
import web.struct.ResultContainer;

import java.util.regex.Pattern;

import static web.printer.PrinterMarker.VERSION_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class YiiVersionProcessor extends AbstractCMSVersionProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final ResultContainer resultContainer;
    @Named(VERSION_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        DissectorResult dissectorResult = JsScriptDissector.dissect(host, request, new String[]{
                "yii.js", "yii.activeForm.js", "yii.validation.js", "yii.captcha.js"
        });

        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, versionSet).prepare(host);
        versionAnalyzer.checkViaSinceScript(
                Pattern.compile("@since\\s(.*?)\\s"),
                dissectorResult.getPaths(),
                dissectorResult.isOverWrittenBasePath(),
                false
        );

        assign(resultContainer, versionSet);
        printer.print(resultContainer);
    }

}
