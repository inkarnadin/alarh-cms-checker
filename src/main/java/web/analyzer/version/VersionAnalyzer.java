package web.analyzer.version;

import lombok.RequiredArgsConstructor;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.maven.artifact.versioning.ComparableVersion;
import web.cms.CMSType;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.parser.TextParser;
import web.parser.XMLParser;
import web.struct.Destination;
import web.analyzer.LogoMap;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static web.http.Headers.CONTENT_TYPE;

@RequiredArgsConstructor
public class VersionAnalyzer {

    private final Request request;
    private final TextParser<String> textParser;
    private final XMLParser<String> xmlParser;
    private final Destination destination;

    private Host host;
    private String mainPageResponseBody = "";
    private Headers mainPageHeaders;
    private String entityType;

    private final AtomicInteger attemptCounter = new AtomicInteger(0);

    public VersionAnalyzer prepare(String protocol, String server, CMSType cmsType) {
        return prepare(protocol, server, cmsType.getName());
    }

    public VersionAnalyzer prepare(String protocol, String server, String entityType) {
        this.entityType = entityType;

        this.host = new Host(protocol, server);
        try (Response response = request.send(host)) {
            mainPageResponseBody = ResponseBodyHandler.readBody(response);
            mainPageHeaders = response.headers();
        }
        return this;
    }

    public void checkViaMainPageGenerator(Pattern[] patterns) {
        String version = "unknown";
        for (Pattern pattern : patterns) {
            textParser.configure(pattern, 1);
            version = textParser.parse(mainPageResponseBody);
        }
        destination.insert(attemptCounter.get(),
                String.format("  ** %s version (check #%s) = %s", entityType, attemptCounter.incrementAndGet(), version));
    }

    public void checkViaXMlFiles(String[] contentTypes, String path) {
        String version = "unknown";

        host.setPath(path);
        try (Response response = request.send(host)) {
            String contentType = response.header(CONTENT_TYPE);
            if (Arrays.asList(contentTypes).contains(contentType)) {
                String body = ResponseBodyHandler.readBody(response);
                version = xmlParser.parse(body);
            }
        }
        destination.insert(attemptCounter.get(),
                String.format("  ** %s version (check #%s) = %s", entityType, attemptCounter.incrementAndGet(), version));
    }

    public void checkViaPageKeywords(String path, Pattern[] patterns) {
        String version = "unknown";

        host.setPath(path);
        try (Response response = request.send(host)) {
            for (Pattern pattern : patterns) {
                String body = ResponseBodyHandler.readBody(response);
                textParser.configure(pattern, 1);
                version = textParser.parse(body);
                break;
            }
        }
        destination.insert(attemptCounter.get(),
                String.format("  ** %s version (check #%s) = %s", entityType, attemptCounter.incrementAndGet(), version));
    }

    public void checkViaLogoFiles(LogoMap logoMap, String[] contentTypes, String[] paths) {
        String version = "unknown";

        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String contentType = response.header(CONTENT_TYPE);
                if (Arrays.asList(contentTypes).contains(contentType)) {
                    ResponseBody body = response.body();
                    long contentLength = (Objects.nonNull(body)) ? body.contentLength() : 0;
                    version = logoMap.getVersion(contentLength);
                }
            }
        }
        destination.insert(attemptCounter.get(),
                String.format("  ** %s version (check #%s) = %s", entityType, attemptCounter.incrementAndGet(), version));
    }

    public void checkViaHeaders(Pattern pattern, String header) {
        String version = "unknown";

        String value = mainPageHeaders.get(header);
        if (Objects.nonNull(value)) {
            textParser.configure(pattern, 1);
            version = textParser.parse(value.toLowerCase());
        }
        destination.insert(attemptCounter.get(),
                String.format("  ** %s version (check #%s) = %s", entityType, attemptCounter.incrementAndGet(), version));
    }

    public void checkViaSinceScript(Pattern pattern, String[] paths) {
        List<ComparableVersion> versions = new ArrayList<>();
        versions.add(new ComparableVersion("unknown"));

        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String body = ResponseBodyHandler.readBody(response);
                Matcher matcher = pattern.matcher(body);

                while (matcher.find()) {
                    versions.add(new ComparableVersion(matcher.group(1)));
                }
            }
        }
        destination.insert(attemptCounter.get(),
                String.format("  ** %s version (check #%s) = %s+", entityType, attemptCounter.incrementAndGet(), Collections.max(versions)));
    }

}
