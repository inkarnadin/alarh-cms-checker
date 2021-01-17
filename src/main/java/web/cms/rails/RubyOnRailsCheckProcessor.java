package web.cms.rails;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.HeaderAnalyzer;
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
import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.LOW;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class RubyOnRailsCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.RUBY_ON_RAILS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(LOW, new String[] { "login" }, new Pattern[] {
                Pattern.compile("authenticity_token"),
                Pattern.compile("turbolinks")
        });
        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(host, result);
        headerAnalyzer.checkViaHeaders(HIGH, BASE_PATH, new String[] {
                "X-Rack-Cache",
                "X-Runtime"
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
