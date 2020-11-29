package web.cms.datalife;

import com.google.inject.Inject;
import web.cms.CMSType;
import web.analyzer.version.VersionAnalyzer;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.Optional;
import java.util.regex.Pattern;

import static web.http.ContentType.IMAGE_JPG;
import static web.http.ContentType.IMAGE_PNG;

public class DataLifeVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<String> textParser;
    private final Destination destination;

    private final DataLifeLogoMap logoMap = new DataLifeLogoMap();

    @Inject
    DataLifeVersionProcessor(Request request,
                             TextParser<String> textParser,
                             Destination destination) {
        this.request = request;
        this.textParser = textParser;
        this.destination = destination;
    }

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, textParser, null, destination);
        versionAnalyzer.prepare(protocol, server, CMSType.DATALIFE_ENGINE);
        versionAnalyzer.checkViaLogoFiles(logoMap, new String[] { IMAGE_JPG, IMAGE_PNG }, new String[] {
                "engine/skins/images/logos.jpg",
                "engine/skins/images/logo.png",
                "templates/Default/images/logotype.png",
                "templates/Default/images/logo.png"
        });
        versionAnalyzer.checkViaPageKeywords("/engine/ajax/updates.php", new Pattern[] {
                Pattern.compile("Актуальная версия скрипта: (.*)")
        });
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
