package web.analyzer.check;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.Headers;
import okhttp3.Response;
import web.env.EnvType;
import web.env.whois.WhoisDto;
import web.env.whois.WhoisLocator;
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
    private String protocol;
    private String server;

    public ExtendEnvironmentAnalyzer prepare(String protocol, String server, EnvType envType) {
        return prepare(protocol, server, envType.getName());
    }

    private ExtendEnvironmentAnalyzer prepare(String protocol, String server, String entityType) {
        this.entityType = entityType;
        this.protocol = protocol;
        this.server = server;

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

    public void checkWhoIs() {
        destination.insert(0, String.format("  ** %s", entityType));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Host host = WhoisLocator.getAvailableWhoisHost(protocol, server);
            try (Response response = request.send(host)) {
                String body = Objects.requireNonNull(response.body()).string();
                WhoisDto dto = objectMapper.readValue(body, WhoisDto.class);

                destination.insert(1,dto.toString());
            }
        } catch (Exception xep) {
            destination.insert(1, xep.getMessage());
            //System.out.println(xep.getMessage());
        }
    }

}
