package web.env.php;

import com.google.inject.Inject;
import lombok.SneakyThrows;
import web.analyzer.version.VersionAnalyzer;
import web.env.EnvType;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.SimpleDestination;

import java.util.regex.Pattern;

public class PhpVersionProcessor extends AbstractProcessor {

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
        System.out.println("PHP version");
    }

}
