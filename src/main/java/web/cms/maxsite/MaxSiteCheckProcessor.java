package web.cms.maxsite;

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

import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.LOW;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class MaxSiteCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.MAXSITE_CMS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        String[] paths = JsScriptDissector.dissect(host, request);

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageGenerator(HIGH, new String[] { "MaxSite CMS" });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("application/maxsite"),
                Pattern.compile("maxsite/plugins"),
                Pattern.compile("maxsite/templates"),
                Pattern.compile("maxsite/common")
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, paths, new Pattern[] {
                Pattern.compile("MaxSite CMS")
        });
        pageAnalyzer.checkViaPageKeywords(LOW, new String[] { "admin" }, new Pattern[] {
                Pattern.compile("flogin"),
                Pattern.compile("flogin_redirect"),
                Pattern.compile("flogin_user"),
                Pattern.compile("flogin_password"),
                Pattern.compile("flogin_submit"),
        });

        assign(destination, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }
}
