package web.cms.datalife;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.Optional;
import java.util.regex.Pattern;

import static web.http.ContentType.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class DataLifeVersionProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.DATALIFE_ENGINE;

    private final Request request;
    private final TextParser<String> textParser;
    private final Destination destination;

    private final DataLifeLogoVersionMap logoMap = new DataLifeLogoVersionMap();
    private final DataLifeScriptVersionMap scriptMap = new DataLifeScriptVersionMap();

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, textParser, null, destination);
        versionAnalyzer.prepare(protocol, server, cmsType);
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
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
