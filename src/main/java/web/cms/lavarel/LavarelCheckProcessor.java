package web.cms.lavarel;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.HeaderAnalyzer;
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

import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.LOW;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class LavarelCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.LAVAREL;

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
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaPaths(LOW, new Integer[] { 200 }, new String[] { "register" });

        assign(destination, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
