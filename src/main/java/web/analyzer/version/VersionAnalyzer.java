package web.analyzer.version;

import lombok.RequiredArgsConstructor;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.maven.artifact.versioning.ComparableVersion;
import web.http.ContentType;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.parser.TextParser;
import web.parser.XMLParser;
import web.analyzer.VersionMap;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static web.http.Headers.CONTENT_TYPE;

@RequiredArgsConstructor
public class VersionAnalyzer {

    private final Request request;
    private final TextParser<String> textParser;
    private final XMLParser<String> xmlParser;
    private final List<ComparableVersion> result;

    private Host host;
    private String mainPageResponseBody = "";
    private Headers mainPageHeaders;

    public VersionAnalyzer prepare(Host host) {
        result.add(new ComparableVersion("<unknown>"));
        this.host = host;
        try (Response response = request.send(host)) {
            mainPageResponseBody = ResponseBodyHandler.readBody(response);
            mainPageHeaders = response.headers();
        }
        return this;
    }

    public void checkViaMainPageMetaTag(Pattern[] patterns) {
        for (Pattern pattern : patterns) {
            textParser.configure(pattern, 1);
            result.add(new ComparableVersion(textParser.parse(mainPageResponseBody)));
        }
    }

    public void checkViaXMlFiles(String[] contentTypes, String path) {
        host.setPath(path);
        try (Response response = request.send(host)) {
            String contentType = response.header(CONTENT_TYPE);
            if (Arrays.asList(contentTypes).contains(contentType)) {
                String body = ResponseBodyHandler.readBody(response);
                result.add(new ComparableVersion(xmlParser.parse(body)));
            }
        }
    }

    public void checkViaPageKeywords(String path, Pattern[] patterns) {
        host.setPath(path);
        try (Response response = request.send(host)) {
            String body = ResponseBodyHandler.readBody(response);
            for (Pattern pattern : patterns) {
                textParser.configure(pattern, 1);
                result.add(new ComparableVersion(textParser.parse(body)));
            }
        }
    }

    public void checkViaLogoFiles(VersionMap logoMap, String[] contentTypes, String[] paths) {
        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String contentType = ContentType.defineContentType(response.header(CONTENT_TYPE));
                if (Arrays.asList(contentTypes).contains(contentType)) {
                    ResponseBody body = response.body();
                    long contentLength = (Objects.nonNull(body)) ? body.contentLength() : 0;
                    result.add(new ComparableVersion(logoMap.getVersion(contentLength)));
                }
            }
        }
    }

    public void checkViaScriptLength(VersionMap logoMap, String[] contentTypes, String[] paths) {
        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String contentType = ContentType.defineContentType(response.header(CONTENT_TYPE));
                if (Arrays.asList(contentTypes).contains(contentType)) {
                    String body = ResponseBodyHandler.readBody(response);
                    long contentLength = body.toCharArray().length;
                    result.add(new ComparableVersion(logoMap.getVersion(contentLength)));
                }
            }
        }
    }

    public void checkViaYear(VersionMap yearMap, String[] paths, Pattern pattern) {
        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String body = ResponseBodyHandler.readBody(response);
                textParser.configure(pattern, 1);

                long year = 0;
                try {
                    year = Long.parseLong(textParser.parse(body));
                } catch (NumberFormatException ignored) {}

                result.add(new ComparableVersion(yearMap.getVersion(year)));
            }
        }
    }

    public void checkViaHeaders(Pattern pattern, String header) {
        String value = mainPageHeaders.get(header);
        if (Objects.nonNull(value)) {
            textParser.configure(pattern, 1);
            result.add(new ComparableVersion(textParser.parse(value)));
        }
    }

    /**
     * Checking version via info into script files
     *
     * @param pattern - what is find
     * @param paths - paths script
     * @param isPrecision - if true - believe that the version is determined exactly, else - just add to output "+"
     */
    public void checkViaSinceScript(Pattern pattern, String[] paths, boolean isPrecision) {
        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String body = ResponseBodyHandler.readBody(response);
                Matcher matcher = pattern.matcher(body);

                while (matcher.find()) {
                    ComparableVersion version = (isPrecision)
                            ? new ComparableVersion(matcher.group(1))
                            : new ComparableVersion(matcher.group(1) + "+");
                    result.add(version);
                }
            }
        }
    }

}
