package web.cms.react;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
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

import static web.analyzer.Importance.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class ReactCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.REACT_JS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        String[] paths = JsScriptDissector.dissect(host, request);

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("ReactDOM"),
                Pattern.compile("ReactDOM-prod"),
                Pattern.compile("react-root"),
                Pattern.compile("React-prod")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(MEDIUM, new Pattern[] {
                Pattern.compile("div id=\"root\"")
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, paths, new Pattern[] {
                Pattern.compile("create-react-context"),
                Pattern.compile("react-dom"),
                Pattern.compile("reactEventHandlers"),
                Pattern.compile("reactContainer"),
                Pattern.compile("reactInternalInstance"),
                Pattern.compile("ReactNative"),
                Pattern.compile("__REACT_DEVTOOLS_GLOBAL_HOOK__"),
                Pattern.compile("__SECRET_INTERNALS_DO_NOT_USE_OR_YOU_WILL_BE_FIRED"),
        });
        pageAnalyzer.checkViaPageKeywords(LOW, paths, new Pattern[] { Pattern.compile("react") });

        assign(destination, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
