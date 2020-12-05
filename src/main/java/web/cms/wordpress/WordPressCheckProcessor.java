package web.cms.wordpress;

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

import static web.analyzer.Importance.*;
import static web.http.ContentType.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WordPressCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.WORDPRESS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);
        mainPageAnalyzer.checkViaMainPageGenerator(HIGH, new String[] {
                "WordPress"
        });
        mainPageAnalyzer.checkViaMainPageKeywords(MEDIUM, new Pattern[] {
                Pattern.compile("wp-featherligh-css"),
                Pattern.compile("contact-form-7-css"),
                Pattern.compile("wp-postratings-css"),
                Pattern.compile("wpfront-scroll-top-css"),
                Pattern.compile("core-front-css")

        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaPaths(LOW, new Integer[] { 200, 403 }, new String[] {
                "wp-content",
                "wp-includes"
        });
        pathAnalyzer.checkViaFiles(MEDIUM, new Integer[] { 200 }, new String[] { APPLICATION_JSON }, new String [] { "wp-json" });
        pathAnalyzer.checkViaFiles(HIGH, new Integer[] { 200 }, new String[] { APPLICATION_JAVASCRIPT, APPLICATION_X_JAVASCRIPT }, new String[] {
                "wp-includes/js/heartbeat.js",
                "wp-includes/js/json2.js",
                "wp-includes/js/wp-api.js",
                "wp-includes/js/wp-util.js",
                "wp-includes/js/wpdialog.js",
                "wp-includes/js/autosave.js",

                "wp-includes/js/wp-embed.min.js"
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(protocol, server, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "wp-login.php" }, new Pattern[] {
                Pattern.compile("Powered by .*WordPress"),
                Pattern.compile("wp-pwd"),
                Pattern.compile("wp-submit"),
                Pattern.compile("wp-core-ui")
        });
        pageAnalyzer.checkViaPageKeywords(LOW, new String[] { "xmlrpc.php" }, new Pattern[] {
                Pattern.compile("XML-RPC server accepts POST requests only")
        });
        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(protocol, server, result);
        headerAnalyzer.checkViaCookies(HIGH, new String[] { "wp-login.php" }, new Pattern[] {
                Pattern.compile("wordpress_test_cookie")
        });
        headerAnalyzer.checkViaHeaderValues(HIGH,new String[] { "wp-json" }, new Pattern[] {
                Pattern.compile("X-WP-Total"),
                Pattern.compile("X-WP-Nonce"),
                Pattern.compile("X-WP-TotalPages")
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
