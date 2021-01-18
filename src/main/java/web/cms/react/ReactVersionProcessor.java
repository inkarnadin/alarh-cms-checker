package web.cms.react;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.JsScriptDissector;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class ReactVersionProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.REACT_JS;

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    public void process() {
        String[] paths = JsScriptDissector.dissect(host, request);
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, destination).prepare(host, cmsType);
        versionAnalyzer.checkViaSinceScript(Pattern.compile("version:\"(.*?)\".*?SECRET_INTERNALS_DO_NOT_USE_OR_YOU_WILL_BE_FIRED"), paths, true);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
