package web.cms.yii;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.JsScriptDissector;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Host;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;
import web.struct.assignment.VersionAssigner;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class YiiVersionProcessor extends AbstractCMSProcessor implements VersionAssigner {

    private static final CMSType cmsType = CMSType.YII;

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    public void process() {
        String[] paths = JsScriptDissector.dissect(host, request, new String[] {
                "yii.js", "yii.activeForm.js", "yii.validation.js", "yii.captcha.js"
        });
        if (paths.length != 0) {
            VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, versionList).prepare(host);
            versionAnalyzer.checkViaSinceScript(Pattern.compile("@since\\s(.*?)\\s"), paths, true);
        }

        assign(destination);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
