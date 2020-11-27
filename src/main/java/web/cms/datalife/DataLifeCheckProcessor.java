package web.cms.datalife;

import com.google.inject.Inject;
import web.cms.CMSType;
import web.cms.analyzer.cms.MainPageAnalyzer;
import web.cms.analyzer.cms.PageAnalyzer;
import web.cms.analyzer.cms.PathAnalyzer;
import web.cms.analyzer.cms.SpecificAnalyzer;
import web.http.Request;
import web.module.annotation.Get;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.parser.TextParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.http.ContentType.*;

public class DataLifeCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Inject
    DataLifeCheckProcessor(@Get Request request,
                           TextParser<Boolean> parser,
                           Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        List<Boolean> result = new ArrayList<>();
        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);

        mainPageAnalyzer.checkViaMainPageGenerator(new String[] { "DataLife Engine" });
        mainPageAnalyzer.checkViaMainPageKeywords(new Pattern[] {
                Pattern.compile("dle_root"),
                Pattern.compile("dle_admin"),
                Pattern.compile("engine/classes"),
                Pattern.compile("engine/templates/Default")
        });
        mainPageAnalyzer.checkViaMainPageScriptName(new Pattern[] {
                Pattern.compile("engine/classes/js/dle_js\\.js")
        });

        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaFiles(new Integer[] { 200, 304 }, new String[] { IMAGE_JPG, IMAGE_PNG }, new String[] {
                "engine/skins/images/logos.jpg",
                "engine/skins/images/logo.png",
                "templates/Default/images/logotype.png",
                "templates/Default/images/logo.png"
        });

        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(protocol, server, result, "admin.php");
        pageAnalyzer.checkViaPageKeywords(new Pattern[] {
                Pattern.compile("DataLife Engine")
        });

        SpecificAnalyzer specificAnalyzer = new SpecificAnalyzer(request, parser).prepare(protocol, server, result);
        specificAnalyzer.checkViaError404Message("administrator", new String[] {
                "[пП]о данному адресу публикаций на сайте не найдено, либо у [вВ]ас нет доступа для просмотра информации по данному адресу"
        });

        long count = result.stream().filter(b -> b).count();
        if (count > 0)
            destination.insert(0, String.format(
                    successMessage,
                    CMSType.DATALIFE_ENGINE.getName(),
                    count,
                    result.size())
            );
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
