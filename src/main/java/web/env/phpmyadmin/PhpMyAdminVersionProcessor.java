package web.env.phpmyadmin;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import web.analyzer.version.VersionAnalyzer;
import web.env.AbstractEnvironmentProcessor;
import web.env.EnvType;
import web.http.Request;
import web.parser.TextParser;
import web.cms.AbstractCMSProcessor;
import web.struct.Destination;
import web.struct.SimpleDestination;

import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class PhpMyAdminVersionProcessor extends AbstractEnvironmentProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    @SneakyThrows
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, destination).prepare(protocol, server, EnvType.PHP_MY_ADMIN);
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
