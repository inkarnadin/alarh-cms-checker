package web.cms.classic.bitrix;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.HeaderAnalyzer;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.ResultContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.*;
import static web.analyzer.Importance.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class BitrixCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.BITRIX;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final ResultContainer resultContainer;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageKeywords(MEDIUM, new Pattern[] {
                Pattern.compile("bitrix/cache"),
                Pattern.compile("bitrix/js"),
                Pattern.compile("bitrix/tools"),
                Pattern.compile("bitrix/components"),
                Pattern.compile("bitrix/panel")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(host, result);
        pathAnalyzer.checkViaPaths(LOW, DENIED_CODES, new String[] {
                "/bitrix/.settings.php",
                "/bitrix/php_interface/dbconn.php",
                "/bitrix/modules/main/lib/db/connection.php"
        });
        pathAnalyzer.checkViaPaths(LOW, ACCEPT_CODES, new String[] {
                "bitrix/admin",
                "bitrix/cache",
                "bitrix/js",
                "bitrix/tools",
                "bitrix/components"
        });
        pathAnalyzer.checkViaFiles(HIGH, SUCCESS_CODES, XML_FILES, new String[] {
                "bitrix/p3p.xml"
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "bitrix/admin" }, new Pattern[] {
                Pattern.compile("bx-admin-prefix"),
                Pattern.compile("BX\\.message"),
                Pattern.compile("BX\\.addClass"),
                Pattern.compile("BX\\.removeClass"),
                Pattern.compile("BX\\.ready"),
                Pattern.compile("BX\\.adminLogin"),
                Pattern.compile("AUTH_NEW_PASSWORD_CONFIRM_WRONG")
        });
        pageAnalyzer.checkViaRobots(HIGH, new Pattern[] {
                Pattern.compile("bitrix")
        });
        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(host, result);
        headerAnalyzer.checkViaCookies(HIGH, BASE_PATH, new Pattern[] {
                Pattern.compile("BITRIX_SM_GUEST_ID"),
                Pattern.compile("BITRIX_SM_LAST_VISIT"),
                Pattern.compile("BITRIX_SM_ABTEST"),
                Pattern.compile("BITRIX_SM_SALE_UID"),
                Pattern.compile("BITRIX_CONVERSION_CONTEXT"),
                Pattern.compile("BX_USER_ID")
        });
        headerAnalyzer.checkViaHeaderValues(HIGH, new String[] { "bitrix/admin" }, new Pattern[] {
                Pattern.compile("Bitrix")
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
