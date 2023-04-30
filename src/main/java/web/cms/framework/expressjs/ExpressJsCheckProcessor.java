package web.cms.framework.expressjs;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.HeaderAnalyzer;
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

import static web.analyzer.AnalyzeConst.BASE_PATH;
import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.LOW;
import static web.struct.CreationHeader.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class ExpressJsCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.EXPRESS_JS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final ResultContainer resultContainer;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageKeywords(LOW, new Pattern[] {
                Pattern.compile("react-md-spinner-animation")
        });
        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(host, result);
        headerAnalyzer.checkViaSpecialHeader(HIGH, BASE_PATH, X_POWERED_BY, Pattern.compile("[Ee]xpress"));

        assign(resultContainer, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<ResultContainer>> transmit() {
        return resultContainer.isSuccess()
                ? new Pair<>(cmsType, Optional.of(resultContainer))
                : new Pair<>(cmsType, Optional.empty());
    }

}
