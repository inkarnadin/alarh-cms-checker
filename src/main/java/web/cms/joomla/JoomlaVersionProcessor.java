package web.cms.joomla;

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

import java.util.Optional;
import java.util.regex.Pattern;

import static web.http.ContentType.APPLICATION_XML;
import static web.http.ContentType.TEXT_XML;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class JoomlaVersionProcessor extends AbstractCMSProcessor {

    private static final CMSType cmsType = CMSType.JOOMLA;

    private final Request request;
    private final XMLParser<String> xmlParser;
    private final TextParser<String> textParser;
    private final Destination destination;

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, textParser, xmlParser, destination);
        versionAnalyzer.prepare(protocol, server, cmsType);
        versionAnalyzer.checkViaMainPageGenerator(new Pattern[] {
                Pattern.compile("<meta name=\"generator\".*Version\\s(.*)\" />")
        });
        versionAnalyzer.checkViaXMlFiles(new String[] { TEXT_XML, APPLICATION_XML }, "administrator/manifests/files/joomla.xml");
        versionAnalyzer.checkViaXMlFiles(new String[] { TEXT_XML, APPLICATION_XML }, "language/en-GB/en-GB.xml");
        versionAnalyzer.checkViaXMlFiles(new String[] { TEXT_XML, APPLICATION_XML }, "administrator/components/com_config/config.xml");
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return destination.isFull()
                ? new Pair<>(cmsType, Optional.of(destination))
                : new Pair<>(cmsType, Optional.empty());
    }

}
