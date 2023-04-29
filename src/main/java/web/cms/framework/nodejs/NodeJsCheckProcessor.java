package web.cms.framework.nodejs;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.MainPageAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.ResultContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.Importance.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class NodeJsCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.REACT_JS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final ResultContainer resultContainer;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageKeywords(LOW, new Pattern[] {
                Pattern.compile("parentNode"),
                Pattern.compile("comments_rules"),
                Pattern.compile("rules_js"),
                Pattern.compile("rules_close_js")
        });

        assign(resultContainer, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<ResultContainer>> transmit() {
        return resultContainer.isSuccess()
                ? new Pair<>(cmsType, Optional.of(resultContainer))
                : new Pair<>(cmsType, Optional.empty());
    }

}
