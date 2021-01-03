package web.env.php;

import com.google.inject.Inject;
import lombok.SneakyThrows;
import web.analyzer.version.VersionAnalyzer;
import web.env.AbstractEnvironmentProcessor;
import web.env.EnvType;
import web.http.Request;
import web.parser.TextParser;
import web.cms.AbstractCMSProcessor;
import web.struct.SimpleDestination;

import java.util.regex.Pattern;

public class PhpVersionProcessor extends AbstractEnvironmentProcessor {

    private final Request request;
    private final TextParser<String> parser;

    @Inject
    PhpVersionProcessor(Request request,
                        TextParser<String> parser) {
        this.request = request;
        this.parser = parser;
    }

    @Override
    @SneakyThrows
    public void process() {
        SimpleDestination destination = new SimpleDestination();
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
