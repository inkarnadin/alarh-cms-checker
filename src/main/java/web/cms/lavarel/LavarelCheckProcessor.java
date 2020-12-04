package web.cms.lavarel;

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

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class LavarelCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(protocol, server, result);
        headerAnalyzer.checkViaCookies(HIGH,new String[] { "", "admin" }, new Pattern[] {
                Pattern.compile("laravel_session")
        });

        assign(destination, result, CMSType.LAVAREL);
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
