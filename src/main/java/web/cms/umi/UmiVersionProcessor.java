package web.cms.umi;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.analyzer.version.VersionAnalyzer;
import web.cms.AbstractCMSProcessor;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.parser.XMLParser;
import web.struct.Destination;
import web.struct.assignment.VersionAssigner;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class UmiVersionProcessor extends AbstractCMSProcessor implements VersionAssigner {

    private static final CMSType cmsType = CMSType.UMI_CMS;

    private final Request request;
    private final XMLParser<String> xmlParser;
    private final TextParser<String> textParser;
    private final Destination destination;

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, textParser, xmlParser, versionList).prepare(host);
        versionAnalyzer.checkViaHeaders(Pattern.compile("(.*)"), "X-CMS-Version");

        assign(destination);
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
