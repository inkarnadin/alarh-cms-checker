package web.cms.maxsite;

import com.google.inject.Inject;
import web.cms.CMSType;
import web.cms.analyzer.cms.MainPageAnalyzer;
import web.http.Request;
import web.module.annotation.Get;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaxSiteCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Inject
    MaxSiteCheckProcessor(@Get Request request,
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

        mainPageAnalyzer.checkViaMainPageGenerator(new String[] { "MaxSite CMS" });
        mainPageAnalyzer.checkViaMainPageKeywords(new String[] {
                "application/maxsite",
                "maxsite/plugins",
                "maxsite/templates",
                "maxsite/common"

        });

        long count = result.stream().filter(b -> b).count();
        if (count > 0)
            destination.insert(0, String.format(
                    successMessage,
                    CMSType.MAXSITE_CMS.getName(),
                    count,
                    result.size())
            );
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
