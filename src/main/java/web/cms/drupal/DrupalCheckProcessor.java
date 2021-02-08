package web.cms.drupal;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.HeaderAnalyzer;
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

import static web.analyzer.AnalyzeConst.BASE_PATH;
import static web.analyzer.Importance.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class DrupalCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.DRUPAL;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageGenerator(HIGH, new String[] { "Drupal" });
        mainPageAnalyzer.checkViaMainPageScriptName(MEDIUM, new Pattern[] {
                Pattern.compile("misc/drupal\\.js")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("data-drupal-link-system-path"),
                Pattern.compile("Drupal\\.settings")
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(LOW, new String[] { "xmlrpc.php" }, new Pattern[] {
                Pattern.compile("XML-RPC server accepts POST requests only")
        });
        pageAnalyzer.checkViaRobots(HIGH, new Pattern[] {
                Pattern.compile("# This file is to prevent the crawling and indexing of certain parts")
        });
        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(host, result);
        headerAnalyzer.checkViaXGenerator(HIGH, BASE_PATH, Pattern.compile("drupal"));
        headerAnalyzer.checkViaHeaders(HIGH, BASE_PATH, new String[] {
                "x-drupal-cache",
                "x-drupal-dynamic-cache"
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
