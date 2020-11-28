package web.cms.bitrix;

import com.google.inject.Inject;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.cms.CMSType;
import web.http.Request;
import web.module.annotation.Get;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class BitrixCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Inject
    BitrixCheckProcessor(@Get Request request,
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
        mainPageAnalyzer.checkViaMainPageKeywords(new Pattern[] {
                Pattern.compile("bitrix/cache"),
                Pattern.compile("bitrix/js"),
                Pattern.compile("bitrix/tools"),
                Pattern.compile("bitrix/components")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaPaths(new Integer[] { 200, 401, 403 }, new String[] {
                "bitrix/cache",
                "bitrix/js",
                "bitrix/tools",
                "bitrix/components"
        });

        long count = result.stream().filter(b -> b).count();
        if (count > 0)
            destination.insert(0, String.format(
                    successMessage,
                    CMSType.BITRIX.getName(),
                    count,
                    result.size())
            );
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
