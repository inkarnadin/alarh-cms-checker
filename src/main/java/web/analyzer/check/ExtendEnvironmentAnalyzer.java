package web.analyzer.check;

import lombok.RequiredArgsConstructor;
import okhttp3.Headers;
import okhttp3.Response;
import web.env.EnvType;
import web.http.Host;
import web.http.Request;
import web.parser.TextParser;
import web.struct.Destination;

import java.util.Objects;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ExtendEnvironmentAnalyzer {

    private final Request request;
    private final TextParser<String> textParser;
    private final Destination destination;

    private Host host;
    private Headers mainPageHeaders;
    private String entityType;

    public ExtendEnvironmentAnalyzer prepare(String protocol, String server, EnvType envType) {
        return prepare(protocol, server, envType.getName());
    }

    private ExtendEnvironmentAnalyzer prepare(String protocol, String server, String entityType) {
        this.entityType = entityType;

        this.host = new Host(protocol, server);
        try (Response response = request.send(host)) {
            mainPageHeaders = response.headers();
        }
        return this;
    }

    public void checkViaHeaders(String header) {
        Pattern pattern = Pattern.compile(".*");
        String webServer = "unknown";

        String value = mainPageHeaders.get(header);
        if (Objects.nonNull(value)) {
            textParser.configure(pattern, 0);
            webServer = textParser.parse(value);
        }
        destination.insert(0,
                String.format("  ** %s = %s", entityType, webServer));
    }

}
