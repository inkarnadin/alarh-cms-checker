package web.cms.classic.joomla;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSVersionProcessor;
import web.http.Request;
import web.parser.TextParser;
import web.parser.XMLParser;
import web.printer.Printer;
import web.struct.ResultContainer;

import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.XML_FILES;
import static web.printer.PrinterMarker.VERSION_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class JoomlaVersionProcessor extends AbstractCMSVersionProcessor {

    private final Request request;
    private final XMLParser<String> xmlParser;
    private final TextParser<String> textParser;
    private final ResultContainer resultContainer;
    @Named(VERSION_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, textParser, xmlParser, versionSet).prepare(host);
        versionAnalyzer.checkViaMainPageGenerator(Pattern.compile("<meta name=\"generator\".*Version\\s(.*)\" />"));
        versionAnalyzer.checkViaXMlFiles(XML_FILES, "administrator/manifests/files/joomla.xml");
        versionAnalyzer.checkViaXMlFiles(XML_FILES, "language/en-GB/en-GB.xml");
        versionAnalyzer.checkViaXMlFiles(XML_FILES, "administrator/components/com_config/config.xml");

        assign(resultContainer, versionSet);
        printer.print(resultContainer);
    }

}
