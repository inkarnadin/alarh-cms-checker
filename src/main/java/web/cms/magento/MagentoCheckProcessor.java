package web.cms.magento;

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

import static web.analyzer.AnalyzeConst.SUCCESS_CODES;
import static web.analyzer.Importance.*;
import static web.http.ContentType.APPLICATION_JAVASCRIPT;
import static web.http.ContentType.TEXT_JAVASCRIPT;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class MagentoCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.MAGENTO;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);
        mainPageAnalyzer.checkViaMainPageKeywords(MEDIUM, new Pattern[] {
                Pattern.compile("Mage\\.Cookies")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("text/x-magento-init"),
                Pattern.compile("mage-translation-storage"),
                Pattern.compile("Magento_PageBuilder"),
                Pattern.compile("Magento_Ui"),
                Pattern.compile("Magento_Theme"),
                Pattern.compile("Magento_Company")
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(protocol, server, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "admin" }, new Pattern[] {
                Pattern.compile("Magento is a trademark of Magento")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaFiles(HIGH, SUCCESS_CODES, new String[] { APPLICATION_JAVASCRIPT, TEXT_JAVASCRIPT }, new String[] {
                "js/mage/captcha.js",
                "js/mage/adminhtml/form.js",
                "js/mage/cookies.js",
                "js/mage/translate.js"
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
