package web.cms.modx;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class ModXCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Boolean> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);
        mainPageAnalyzer.checkViaMainPageKeywords(new Pattern[] {
                Pattern.compile("assets/templates"),
                Pattern.compile("assets/cache")
        });

        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaPaths(new Integer[] { 200, 403 }, new String[] {
                "assets/templates",
                "assets/images",
                "assets/cache",
                "assets/js",
                "manager"
        });

        long count = result.stream().filter(b -> b).count();
        if (count > 0)
            destination.insert(0, String.format(
                    successMessage,
                    CMSType.MODX.getName(),
                    count,
                    result.size())
            );
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
