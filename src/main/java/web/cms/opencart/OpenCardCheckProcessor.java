package web.cms.opencart;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.cms.CMSType;
import web.http.ContentType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.Importance.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class OpenCardCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);
        mainPageAnalyzer.checkViaMainPageKeywords(MEDIUM,new Pattern[] {
                Pattern.compile("cart-panel"),
                Pattern.compile("cart-url"),
                Pattern.compile("cart-alert"),
                Pattern.compile("cart-product"),
                Pattern.compile("cart-no"),
                Pattern.compile("cart-wishlist"),
                Pattern.compile("cart-quantity")
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(protocol, server, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "admin" }, new Pattern[] {
                Pattern.compile("ocStore")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaFiles(LOW, new Integer[] { 200, 304 }, new String[] { ContentType.APPLICATION_JAVASCRIPT }, new String[] {
                "admin/view/javascript/common.js",
                "catalog/view/theme/madeshop/script/common.js",
                "catalog/view/theme/madeshop/script/inputmask.min.js"
        });

        assign(destination, result, CMSType.OPENCART);
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
