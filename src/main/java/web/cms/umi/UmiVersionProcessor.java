package web.cms.umi;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.Importance;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.parser.XMLParser;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.http.ContentType.APPLICATION_XML;
import static web.http.ContentType.TEXT_XML;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class UmiVersionProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.UMI_CMS;

    private final Request request;
    private final XMLParser<String> xmlParser;
    private final TextParser<String> textParser;
    private final Destination destination;

    @Override
    public void process() {
        List<Pair<Boolean, Importance>> result = new ArrayList<>();

        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, textParser, xmlParser, destination);
        versionAnalyzer.prepare(host, cmsType);
        versionAnalyzer.checkViaHeaders(Pattern.compile("(.*)"), "X-CMS-Version");

        assign(destination, result, cmsType);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
