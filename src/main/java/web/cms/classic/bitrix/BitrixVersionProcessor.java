package web.cms.classic.bitrix;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.analyzer.VersionMap;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSVersionProcessor;
import web.http.Request;
import web.parser.TextParser;
import web.parser.XMLParser;
import web.printer.Printer;
import web.struct.ResultContainer;

import java.util.regex.Pattern;

import static web.printer.PrinterMarker.VERSION_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class BitrixVersionProcessor extends AbstractCMSVersionProcessor {

    private final Request request;
    private final XMLParser<String> xmlParser;
    private final TextParser<String> textParser;
    private final ResultContainer resultContainer;
    @Named(VERSION_PRINTER)
    private final Printer printer;

    private final VersionMap yearMap = new BitrixYearVersionMap();

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, textParser, xmlParser, versionSet).prepare(host);
        versionAnalyzer.checkViaYear(yearMap, new String[] { "bitrix/admin" }, Pattern.compile("Управление сайтом.*Битрикс, (\\d{4})"));

        assign(resultContainer, versionSet);
        printer.print(resultContainer);
    }

}
