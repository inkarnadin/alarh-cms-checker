package web.cms.joomla;

import com.google.inject.Inject;
import web.cms.CMSType;
import web.cms.analyzer.cms.MainPageAnalyzer;
import web.cms.analyzer.cms.PageAnalyzer;
import web.cms.analyzer.cms.PathAnalyzer;
import web.module.annotation.Get;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.http.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.http.ContentType.APPLICATION_XML;
import static web.http.ContentType.TEXT_XML;

public class JoomlaCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Inject
    JoomlaCheckProcessor(@Get Request request,
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
        mainPageAnalyzer.checkViaMainPageGenerator(new String[] { "Joomla" });

        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaPaths(new Integer[] { 200, 304, 401, 403 }, new String[] {
                "administrator/components/com_config"
        });
        pathAnalyzer.checkViaFiles(new Integer[] { 200, 304 }, new String[] { TEXT_XML, APPLICATION_XML }, new String[] {
                "language/en-GB/en-GB.xml",
                "administrator/manifests/files/joomla.xml",
                "administrator/components/com_config/config.xml",
        });

        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(protocol, server, result, "administrator");
        pageAnalyzer.checkViaPageKeywords(new Pattern[] {
                Pattern.compile("login-joomla"),
                Pattern.compile("joomla-script-options")
        });

        long count = result.stream().filter(b -> b).count();
        if (count > 0)
            destination.insert(0, String.format(
                    successMessage,
                    CMSType.JOOMLA.getName(),
                    count,
                    result.size())
            );
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
