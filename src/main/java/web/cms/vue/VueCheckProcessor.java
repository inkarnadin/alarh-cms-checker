package web.cms.vue;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.DissectorResult;
import web.analyzer.Importance;
import web.analyzer.JsScriptDissector;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.MEDIUM;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class VueCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.VUE_JS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        DissectorResult dissectorResult = JsScriptDissector.dissect(host, request);
        String[] paths = dissectorResult.getPaths();
        boolean isOverWrittenBasePath = dissectorResult.isOverWrittenBasePath();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageScriptName(HIGH, new Pattern[] {
                Pattern.compile("vue-modal"),
                Pattern.compile("vue-widget"),
                Pattern.compile("vue-component")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("vue-handle-error\\.js"),
                Pattern.compile("vue\\.js")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(MEDIUM, new Pattern[] {
                Pattern.compile("build/js/utils\\.js")
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, paths, new Pattern[] { Pattern.compile("[Vv]ue\\.js") }, isOverWrittenBasePath);
        pageAnalyzer.checkViaPageKeywords(HIGH, paths, new Pattern[] {
                Pattern.compile("VUE_SSR_CONTEXT"),
                Pattern.compile("VUE_DEVTOOLS_GLOBAL_HOOK"),
                Pattern.compile("VUE_ENV"),
        }, isOverWrittenBasePath);

        assign(destination, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
