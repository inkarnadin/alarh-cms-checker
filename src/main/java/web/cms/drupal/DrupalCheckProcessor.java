package web.cms.drupal;

import com.google.inject.Inject;
import web.cms.CMSType;
import web.cms.analyzer.cms.MainPageAnalyzer;
import web.http.Request;
import web.module.annotation.Get;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class DrupalCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Inject
    DrupalCheckProcessor(@Get Request request,
                         TextParser<Boolean> parser,
                         Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        List<Boolean> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);
        mainPageAnalyzer.checkViaMainPageGenerator(new String[] { "Drupal" });
        mainPageAnalyzer.checkViaMainPageScriptName(new Pattern[] {
                Pattern.compile("misc/drupal\\.js")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(new String[] {
                "data-drupal-link-system-path",
                "Drupal\\.settings"
        });

        long count = result.stream().filter(b -> b).count();
        if (count > 0)
            destination.insert(0, String.format(
                    successMessage,
                    CMSType.DRUPAL.getName(),
                    count,
                    result.size())
            );
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
