package web.env.php;

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
public class PhpVersionProcessor extends AbstractEnvironmentProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    @SneakyThrows
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, destination).prepare(protocol, server, EnvType.PHP);
        versionAnalyzer.checkViaHeaders(Pattern.compile("php/(.*)"), "x-powered-by");
        //versionAnalyzer.checkViaHeaders(Pattern.compile("PHP/"), "Server");
        versionAnalyzer.checkViaPageKeywords("phpinfo.php", new Pattern[] {
                Pattern.compile(">PHP Version (.*?)<")
        });

        System.out.println(destination.fetch().get(0));
        System.out.println(destination.fetch().get(1));
    }

}
