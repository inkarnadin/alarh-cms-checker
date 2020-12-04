package web.cms.rails;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.HeaderAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.LOW;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class RubyOnRailsCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(protocol, server, result);
        pageAnalyzer.checkViaPageKeywords(LOW, new String[] { "login" }, new Pattern[] {
                Pattern.compile("authenticity_token")
        });
        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(protocol, server, result);
        headerAnalyzer.checkViaHeaders(HIGH, new String[] { "" }, new String[] {
                "X-Rack-Cache",
                "X-Runtime"
        });

        assign(destination, result, CMSType.RUBY_ON_RAILS);
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
