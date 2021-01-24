package web.cms.datalife;

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

import static web.http.ContentType.*;
import static web.printer.PrinterMarker.VERSION_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class DataLifeVersionProcessor extends AbstractCMSVersionProcessor {

    private final Request request;
    private final TextParser<String> textParser;
    private final Destination destination;
    @Named(VERSION_PRINTER)
    private final Printer printer;

    private final DataLifeLogoVersionMap logoMap = new DataLifeLogoVersionMap();
    private final DataLifeScriptVersionMap scriptMap = new DataLifeScriptVersionMap();

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, textParser, null, versionList).prepare(host);
        versionAnalyzer.checkViaLogoFiles(logoMap, new String[] { IMAGE_JPG, IMAGE_PNG }, new String[] {
                "engine/skins/images/logos.jpg",
                "engine/skins/images/logo.png",
                "templates/Default/images/logotype.png",
                "templates/Default/images/logo.png"
        });
        versionAnalyzer.checkViaScriptLength(scriptMap, new String[] { APPLICATION_X_JAVASCRIPT, APPLICATION_JAVASCRIPT }, new String[] {
                "engine/classes/js/dle_js.js"
        });
        versionAnalyzer.checkViaPageKeywords("/engine/ajax/updates.php", new Pattern[] {
                Pattern.compile("Актуальная версия скрипта: (.*)")
        });

        assign(destination, versionList);
        printer.print(destination);
    }

}
