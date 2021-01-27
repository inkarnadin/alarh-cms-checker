package web.cms.modx;

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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.ACCEPT_CODES;
import static web.analyzer.Importance.HIGH;
import static web.analyzer.Importance.LOW;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class ModXCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.MODX;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

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
        pathAnalyzer.checkViaPaths(LOW, ACCEPT_CODES, new String[] {
                "assets/templates",
                "assets/images",
                "assets/cache",
                "assets/js",
                "manager"
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

        assign(destination, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
