package web.env.php;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import web.analyzer.version.VersionAnalyzer;
import web.struct.assignment.VersionAssigner;
import web.env.AbstractEnvironmentProcessor;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class PhpCheckProcessor extends AbstractEnvironmentProcessor implements VersionAssigner {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    @SneakyThrows
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, versionList).prepare(host);
        versionAnalyzer.checkViaHeaders(Pattern.compile("php/([\\.\\d]*)"), "x-powered-by");
        versionAnalyzer.checkViaHeaders(Pattern.compile("php/([\\.\\d]*)"), "server");
        versionAnalyzer.checkViaPageKeywords("phpinfo.php", new Pattern[] {
                Pattern.compile(">PHP Version (.*?)<")
        });

        assign(destination);
    }

}
