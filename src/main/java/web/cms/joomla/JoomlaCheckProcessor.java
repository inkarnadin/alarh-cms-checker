package web.cms.joomla;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.cms.CMSType;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.http.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.LOW;
import static web.http.ContentType.APPLICATION_XML;
import static web.http.ContentType.TEXT_XML;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class JoomlaCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);
        mainPageAnalyzer.checkViaMainPageGenerator(HIGH, new String[] { "Joomla" });

        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaPaths(LOW, new Integer[] { 200, 304, 401, 403 }, new String[] {
                "administrator/components/com_config"
        });
        pathAnalyzer.checkViaFiles(HIGH, new Integer[] { 200, 304 }, new String[] { TEXT_XML, APPLICATION_XML }, new String[] {
                "language/en-GB/en-GB.xml",
                "administrator/manifests/files/joomla.xml",
                "administrator/components/com_config/config.xml",
        });

        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(protocol, server, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "administrator" }, new Pattern[] {
                Pattern.compile("login-joomla"),
                Pattern.compile("joomla-script-options")
        });

        assign(destination, result, CMSType.JOOMLA);
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
