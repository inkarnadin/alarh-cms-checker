package web.cms.wix;

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
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.BASE_PATH;
import static web.analyzer.Importance.HIGH;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WixCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.WIX;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageGenerator(HIGH, new String[] { "Wix\\.com Website Builder" });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("wix-fedops"),
                Pattern.compile("wix-code-sdk-providers"),
                Pattern.compile("wix-resize"),
                Pattern.compile("wix-image"),
                Pattern.compile("wix-warmup-data"),
        });
        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(host, result);
        headerAnalyzer.checkViaHeaders(HIGH, BASE_PATH, new String[] { "x-wix-request-id" });

        assign(destination, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
