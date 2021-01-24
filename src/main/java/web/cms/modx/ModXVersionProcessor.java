package web.cms.modx;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import org.apache.maven.artifact.versioning.ComparableVersion;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSVersionProcessor;
import web.http.Request;
import web.parser.TextParser;
import web.printer.Printer;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static web.printer.PrinterMarker.VERSION_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class ModXVersionProcessor extends AbstractCMSVersionProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;
    @Named(VERSION_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        List<ComparableVersion> versionList = new ArrayList<>();

        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, versionList).prepare(host);
        versionAnalyzer.checkViaPageKeywords("manager", new Pattern[] {
                Pattern.compile("(Revolution|Evolution)")
        });

        assign(destination, versionList);
        printer.print(destination);
    }

}
