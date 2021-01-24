package web.cms.host;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.HeaderAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.struct.assignment.CMSResultAssigner;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.*;
import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.LOW;
import static web.http.ContentType.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class HostCmsCheckProcessor extends AbstractCMSProcessor implements CMSResultAssigner {

    private static final CMSType cmsType = CMSType.HOST_CMS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(host, result);
        headerAnalyzer.checkViaHeaderValues(HIGH, BASE_PATH, new Pattern[] {
                Pattern.compile("HostCMS")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(host, result);
        pathAnalyzer.checkViaPaths(LOW, DENIED_CODES, new String[] { "modules", "admin" });
        pathAnalyzer.checkViaPaths(HIGH, DENIED_CODES, new String[] { "hostcmsfiles" });
        pathAnalyzer.checkViaFiles(HIGH, SUCCESS_CODES, new String[] { APPLICATION_X_JAVASCRIPT }, new String[] {
                "modules/skin/bootstrap/js/hostcms.js"
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "admin" }, new Pattern[] {
                Pattern.compile("Вход в центр администрирования HostCMS"),
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
