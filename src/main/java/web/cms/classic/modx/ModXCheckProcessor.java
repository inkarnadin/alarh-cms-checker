package web.cms.classic.modx;

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
import web.struct.ResultContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.*;
import static web.analyzer.Importance.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class ModXCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.MODX;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final ResultContainer resultContainer;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageKeywords(LOW, new Pattern[] {
                Pattern.compile("assets/templates"),
                Pattern.compile("assets/components"),
                Pattern.compile("assets/cache"),
                Pattern.compile("assets/gallery"),
                Pattern.compile("assets/images"),
                Pattern.compile("assets/snippets")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("easyCommConfig"),
                Pattern.compile("miniShop2Config"),
                Pattern.compile("assets/components/easycomm"),
                Pattern.compile("assets/components/minishop2"),
                Pattern.compile("assets/components/gallery"),
                Pattern.compile("assets/components/ajaxform")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(host, result);
        pathAnalyzer.checkViaFiles(HIGH, ACCEPT_CODES, IMAGE_FILES, new String[] {
                "manager/templates/default/images/modx-logo-color.svg"
        });
        pathAnalyzer.checkViaPaths(LOW, ACCEPT_CODES, new String[] {
                "assets/templates",
                "assets/images",
                "assets/cache",
                "assets/js",
                "manager"
        });
        pathAnalyzer.checkViaPaths(MEDIUM, DENIED_CODES, new String[] {
                "core/",
                "connectors/",
                "assets/components/"
        });

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");

        request.richHeaders(headers);

        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "manager" }, new Pattern[] {
                Pattern.compile("modx-form"),
                Pattern.compile("modx-fl-link"),
                Pattern.compile("modx-login-password"),
                Pattern.compile("modx-fl-btn"),
                Pattern.compile("modx-login-language-select"),
                Pattern.compile("Evolution CMS Manager Login"),
        });
        pageAnalyzer.checkViaRobots(HIGH, new Pattern[] {
                Pattern.compile("# Default modx exclusions")
        });
        pageAnalyzer.checkViaRobots(LOW, new Pattern[] {
                Pattern.compile("core/?"),
                Pattern.compile("connectors/?"),
                Pattern.compile("assets/.*")
        });

        assign(resultContainer, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<ResultContainer>> transmit() {
        return resultContainer.isSuccess()
                ? new Pair<>(cmsType, Optional.of(resultContainer))
                : new Pair<>(cmsType, Optional.empty());
    }

}
