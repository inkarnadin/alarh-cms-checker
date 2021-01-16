package web.cms.joomla;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
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

import static web.analyzer.AnalyzeConst.ACCEPT_CODES;
import static web.analyzer.AnalyzeConst.SUCCESS_CODES;
import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.LOW;
import static web.http.ContentType.APPLICATION_XML;
import static web.http.ContentType.TEXT_XML;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class JoomlaCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.JOOMLA;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageGenerator(HIGH, new String[] { "Joomla" });

        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(host, result);
        pathAnalyzer.checkViaPaths(LOW, ACCEPT_CODES, new String[] {
                "administrator",
                "administrator/components/com_config"
        });
        pathAnalyzer.checkViaFiles(HIGH, SUCCESS_CODES, new String[] { TEXT_XML, APPLICATION_XML }, new String[] {
                "language/en-GB/en-GB.xml",
                "administrator/manifests/files/joomla.xml",
                "administrator/components/com_config/config.xml",
        });

        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "administrator" }, new Pattern[] {
                Pattern.compile("login-joomla"),
                Pattern.compile("joomla-script-options")
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
