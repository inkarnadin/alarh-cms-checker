package web.cms.classic.opencart;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.HeaderAnalyzer;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.*;
import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.LOW;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class OpenCardCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.OPENCART;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageKeywords(LOW, new Pattern[] {
                Pattern.compile("cart-panel"),
                Pattern.compile("cart-url"),
                Pattern.compile("cart-alert"),
                Pattern.compile("cart-no"),
                Pattern.compile("cart-wishlist"),
                Pattern.compile("cart-quantity"),
                Pattern.compile("cart-total"),
                Pattern.compile("cart-effect"),
                Pattern.compile("cart-toggle")
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "admin" }, new Pattern[] {
                Pattern.compile("route=common/forgotten")
        });
        pageAnalyzer.checkViaRobots(LOW, new Pattern[] {
                Pattern.compile("route=account"),
                Pattern.compile("route=affiliate"),
                Pattern.compile("route=checkout")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(host, result);
        pathAnalyzer.checkViaFiles(LOW, SUCCESS_CODES, SCRIPT_FILES, new String[] {
                "admin/view/javascript/common.js",
                "catalog/view/javascript/common.js",
                "catalog/view/javascript/support.js"
        });
        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(host, result);
        headerAnalyzer.checkViaCookies(HIGH, BASE_PATH, new Pattern[] { Pattern.compile("OCSESSID") });

        assign(destination, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
