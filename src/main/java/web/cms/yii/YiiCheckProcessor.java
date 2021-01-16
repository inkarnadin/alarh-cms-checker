package web.cms.yii;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.JsScriptDissector;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Host;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.DENIED_CODES;
import static web.analyzer.Importance.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class YiiCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.YII;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);
        mainPageAnalyzer.checkViaMainPageScriptName(HIGH, new Pattern[] { Pattern.compile("<script src=\".*(yii.js).*\"></script>") });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] { Pattern.compile("YII_CSRF_TOKEN") });
        mainPageAnalyzer.checkViaMainPageKeywords(MEDIUM, new Pattern[] { Pattern.compile("/assets/[\\w\\d]{8}/") });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(protocol, server, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "login", "admin/login", "admin/site/login" }, new Pattern[] {
                Pattern.compile("Powered by.*Yii Framework"),
                Pattern.compile("field-loginform-username"),
                Pattern.compile("field-loginform-password"),
                Pattern.compile("field-loginform-email"),
                Pattern.compile("loginform-rememberme"),
                Pattern.compile("loginform-password"),
                Pattern.compile("loginform-username"),
                Pattern.compile("yii\\.validation"),
                Pattern.compile("yii\\.activeForm"),
                Pattern.compile("LoginForm")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaPaths(LOW, DENIED_CODES, new String[] { "vendor/yiisoft/yii2" });

        assign(destination, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
