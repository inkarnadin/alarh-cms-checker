package web.cms.framework.nuxt;

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
import web.struct.ResultContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.Importance.HIGH;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class NuxtCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.NUXT_JS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final ResultContainer resultContainer;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        DissectorResult dissectorResult = JsScriptDissector.dissect(host, request);
        String[] paths = dissectorResult.getPaths();
        boolean isOverWrittenBasePath = dissectorResult.isOverWrittenBasePath();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("data-n-head="),
                Pattern.compile("data-hid="),
                Pattern.compile("_nuxt")
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, paths, new Pattern[] {
                Pattern.compile("nuxt-link")
        }, isOverWrittenBasePath);

        assign(resultContainer, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<ResultContainer>> transmit() {
        return resultContainer.isSuccess()
                ? new Pair<>(cmsType, Optional.of(resultContainer))
                : new Pair<>(cmsType, Optional.empty());
    }

}
