package web.cms.drupal;

import com.google.inject.Inject;
import web.cms.CMSType;
import web.cms.analyzer.version.VersionAnalyzer;
import web.http.Request;
import web.module.annotation.Get;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.Optional;
import java.util.regex.Pattern;

public class DrupalVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Inject
    DrupalVersionProcessor(@Get Request request,
                           TextParser<String> parser,
                           Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, destination);
        versionAnalyzer.prepare(protocol, server, CMSType.DRUPAL);
        versionAnalyzer.checkViaMainPageGenerator(new Pattern[] {
                Pattern.compile("<meta name=\"[gG]enerator\" content=\"Drupal\\s(.+?)\\s")
        });
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
