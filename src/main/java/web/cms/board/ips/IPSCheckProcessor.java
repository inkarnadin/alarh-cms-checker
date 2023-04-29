package web.cms.board.ips;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.MainPageAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.ResultContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.Importance.HIGH;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class IPSCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.IPS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final ResultContainer resultContainer;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageScriptName(HIGH, new Pattern[] {
                Pattern.compile("jscripts/ips_ipsclass\\.js"),
                Pattern.compile("jscripts/ipb_global\\.js"),
                Pattern.compile("jscripts/ips_menu\\.js"),
                Pattern.compile("jscripts/ips_xmlhttprequest\\.js"),
                Pattern.compile("jscripts/ipb_global_xmlenhanced\\.js"),
                Pattern.compile("jscripts/dom-drag\\.js")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("ipbwrapper"),
                Pattern.compile("ipb_var_cookie_domain"),
                Pattern.compile("ipb_var_cookie_path"),
                Pattern.compile("ipb\\.html"),
                Pattern.compile("ipsQueryLog"),
                Pattern.compile("ipsCachingLog"),
                Pattern.compile("ipsJS_show"),
                Pattern.compile("IPS, Inc")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("Powered by Invision Community")
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
