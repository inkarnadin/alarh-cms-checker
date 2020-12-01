package web.env.phpmyadmin;

import com.google.inject.Inject;
import lombok.SneakyThrows;
import web.analyzer.version.VersionAnalyzer;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.SimpleDestination;

import java.util.regex.Pattern;

public class PhpMyAdminVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<String> parser;

    @Inject
    PhpMyAdminVersionProcessor(Request request,
                               TextParser<String> parser) {
        this.request = request;
        this.parser = parser;
    }

    @Override
    @SneakyThrows
    public void process() {
        SimpleDestination destination = new SimpleDestination();
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, destination).prepare(protocol, server, "PhpMyAdmin");
        versionAnalyzer.checkViaPageKeywords("phpmyadmin/doc/html/index.html", new Pattern[] {
                Pattern.compile("<title>.*phpMyAdmin\\s(.*?)\\s")
        });
        versionAnalyzer.checkViaPageKeywords("phpmyadmin/Documentation.html", new Pattern[] {
                Pattern.compile("<title>.*phpMyAdmin\\s(.*?)\\s")
        });
        System.out.println(destination.fetch().get(0));
        System.out.println(destination.fetch().get(1));
    }

}
