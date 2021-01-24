package web.cms.datalife;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.analyzer.check.SpecificAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;
import web.struct.assignment.CMSResultAssigner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.SUCCESS_CODES;
import static web.analyzer.Importance.*;
import static web.http.ContentType.IMAGE_JPG;
import static web.http.ContentType.IMAGE_PNG;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class DataLifeCheckProcessor extends AbstractCMSProcessor implements CMSResultAssigner {

    private static final CMSType cmsType = CMSType.DATALIFE_ENGINE;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();
        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);

        mainPageAnalyzer.checkViaMainPageGenerator(HIGH, new String[] { "DataLife Engine" });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("dle_root"),
                Pattern.compile("dle_admin"),
                Pattern.compile("engine/classes"),
                Pattern.compile("engine/templates/Default")
        });
        mainPageAnalyzer.checkViaMainPageScriptName(HIGH, new Pattern[] {
                Pattern.compile("engine/classes/js/dle_js\\.js")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(host, result);
        pathAnalyzer.checkViaFiles(LOW, SUCCESS_CODES, new String[] { IMAGE_JPG, IMAGE_PNG }, new String[] {
                "engine/skins/images/logos.jpg",
                "engine/skins/images/logo.png",
                "templates/Default/images/logotype.png",
                "templates/Default/images/logo.png"
        });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(MEDIUM, new String[] { "admin.php" }, new Pattern[] {
                Pattern.compile("DataLife Engine")
        });
        SpecificAnalyzer specificAnalyzer = new SpecificAnalyzer(request, parser).prepare(host, result);
        specificAnalyzer.checkViaError404Message(LOW, "administrator", new String[] {
                "[пП]о данному адресу публикаций на сайте не найдено, либо у [вВ]ас нет доступа для просмотра информации по данному адресу"
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
