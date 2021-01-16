package web.cms.yii;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.analyzer.JsScriptDissector;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class YiiVersionProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.YII;

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    public void process() {
        Host host = new Host(protocol, server);
        List<String> paths = JsScriptDissector.dissect(host, request, new String[] {
                "yii.js", "yii.activeForm.js", "yii.validation.js", "yii.captcha.js"
        });
        if (!paths.isEmpty()) {
            VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, destination).prepare(protocol, server, cmsType);
            versionAnalyzer.checkViaSinceScript(Pattern.compile("@since\\s(.*?)\\s"), paths.toArray(new String[0]));
        }
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
