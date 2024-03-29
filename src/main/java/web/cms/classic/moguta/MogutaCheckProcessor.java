package web.cms.classic.moguta;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.DublinCoreSignStorage;
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

import static web.analyzer.AnalyzeConst.BASE_PATH;
import static web.analyzer.AnalyzeConst.SUCCESS_CODES;
import static web.analyzer.Importance.*;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class MogutaCheckProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.MOGUTA_CMS;

    private final Request request;
    private final TextParser<Boolean> parser;
    private final ResultContainer resultContainer;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(host, result);
        mainPageAnalyzer.checkViaMainPageKeywords(LOW, DublinCoreSignStorage.getElements());
        mainPageAnalyzer.checkViaMainPageMetaTag(HIGH, new String[] { "mogutacms" });
        mainPageAnalyzer.checkViaMainPageKeywords(MEDIUM, new Pattern[] {
                Pattern.compile("<!--Базовые метатеги страницы--> "),
                Pattern.compile("<!--Кодировка страницы-->"),
                Pattern.compile("<!--Стили для шаблона-->"),
                Pattern.compile("<!--Мобильные стили-->"),
                Pattern.compile("<!--Библиотека для jQuery-->"),
                Pattern.compile("<!--Скрипты плагинов и модулей движка-->")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(MEDIUM, new Pattern[] {
                Pattern.compile("mg-form-designer"),
                Pattern.compile("mg-title-form"),
                Pattern.compile("mg-title-field-form"),
                Pattern.compile("mg-field"),
                Pattern.compile("mg-form-popup"),
                Pattern.compile("mg-form-for-download")
        });
        mainPageAnalyzer.checkViaMainPageKeywords(HIGH, new Pattern[] {
                Pattern.compile("mg-core"),
                Pattern.compile("mg-templates"),
                Pattern.compile("mg-plugins")
        });
        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(host, result);
        pathAnalyzer.checkViaPaths(LOW, SUCCESS_CODES, new String[] { "enter" });
        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(host, result);
        pageAnalyzer.checkViaPageKeywords(HIGH, new String[] { "mg-admin" }, new Pattern[] {
                Pattern.compile("Moguta\\.CMS")
        });
        pageAnalyzer.checkViaRobots(LOW, new Pattern[] {
               Pattern.compile("mg-admin"),
               Pattern.compile("mg-formvalid"),
               Pattern.compile("mg-core"),
               Pattern.compile("mg-pages"),
               Pattern.compile("mg-plugins"),
               Pattern.compile("mg-templates")
        });
        HeaderAnalyzer headerAnalyzer = new HeaderAnalyzer(request, parser).prepare(host, result);
        headerAnalyzer.checkViaHeaderValues(HIGH, BASE_PATH, new Pattern[] {
                Pattern.compile("mg_to_script")
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
