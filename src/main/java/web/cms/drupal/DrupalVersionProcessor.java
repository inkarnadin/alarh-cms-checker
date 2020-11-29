package web.cms.drupal;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import web.cms.CMSType;
import web.analyzer.version.VersionAnalyzer;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class DrupalVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, destination);
        versionAnalyzer.prepare(protocol, server, CMSType.DRUPAL);
        versionAnalyzer.checkViaMainPageGenerator(new Pattern[] {
                Pattern.compile("<meta name=\"[gG]enerator\" content=\"Drupal\\s(.+?)\\s")
        });
        versionAnalyzer.checkViaHeaders(Pattern.compile("drupal\\s(.*)\\s"), "x-generator");
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
