package web.analyzer.check;

import lombok.RequiredArgsConstructor;
import okhttp3.Headers;
import okhttp3.Response;
import web.env.EnvType;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
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

    public void checkPhpMyAdmin(String[] paths) {
        Pattern pattern = Pattern.compile("<title>.*phpMyAdmin\\s(.*?)\\s");
        String version = "";

        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String body = ResponseBodyHandler.readBody(response);
                textParser.configure(pattern, 1);
                version = textParser.parse(body);
                if (!"unknown".equals(version))
                    break;
            }
        }
        destination.insert(0, String.format("  ** %s version = %s", entityType, version));
    }

    public void checkWebServer(String header) {
        String value = mainPageHeaders.get(header);
        String webServer = Objects.nonNull(value) ? value : "unknown";

        destination.insert(0, String.format("  ** %s = %s", entityType, webServer));
    }

}
