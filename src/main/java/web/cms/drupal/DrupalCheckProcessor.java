package web.cms.drupal;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.cms.CMSType;
import web.analyzer.check.MainPageAnalyzer;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.MEDIUM;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class DrupalCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);
        mainPageAnalyzer.checkViaMainPageGenerator(HIGH, new String[] { "Drupal" });
        mainPageAnalyzer.checkViaMainPageScriptName(MEDIUM, new Pattern[] {
                Pattern.compile("misc/drupal\\.js")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("data-drupal-link-system-path"),
                Pattern.compile("Drupal\\.settings")
        });
        mainPageAnalyzer.checkViaMainPageHeaders(HIGH, new String[] {
                "x-drupal-cache",
                "x-drupal-dynamic-cache"
        });
        mainPageAnalyzer.checkViaMainPageXGeneratorHeader(HIGH, Pattern.compile("drupal"));

        assign(destination, result, CMSType.DRUPAL);
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
