package web.analyzer.version;

import lombok.RequiredArgsConstructor;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.maven.artifact.versioning.ComparableVersion;
import web.analyzer.VersionMap;
import web.http.ContentType;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.parser.TextParser;
import web.parser.XMLParser;
import web.struct.Validator;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static web.http.Headers.CONTENT_TYPE;

@RequiredArgsConstructor
public class VersionAnalyzer {

    private final Request request;
    private final TextParser<String> textParser;
    private final XMLParser<String> xmlParser;
    private final Set<ComparableVersion> result;

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
            result.add(new ComparableVersion(textParser.parse(value.toLowerCase())));
        }
    }

    /**
     * Check version via info into script files.
     *
     * @param pattern pattern of expression
     * @param paths paths to scripts
     * @param isOverwrittenBasePath if true - base path was overwritten
     * @param isPrecision if true - believe that the version is determined exactly, else - just add to output "+"
     */
    public void checkViaSinceScript(Pattern pattern, String[] paths, boolean isOverwrittenBasePath, boolean isPrecision) {
        for (String path : paths) {
            if (isOverwrittenBasePath) {
                host.setOverWrittenBasePath(path);
            } else {
                host.setPath(path);
            }
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
        host.setOverWrittenBasePath(null);
        host.setPath(null);
    }

    /**
     * Check version via info about version, found on main page.
     * Exclude potential plugins versions && wrong numeration version via validator.
     *
     * @param path - path to page
     * @param patterns - patterns for search
     * @param excludes - list of excludes keywords
     * @param validator - version validator
     */
    public void checkViaMainPageLookVersion(String path, Pattern[] patterns, String[] excludes, Validator validator) {
        host.setPath(path);
        try (Response response = request.send(host)) {
            String body = ResponseBodyHandler.readBody(response);
            for (Pattern pattern : patterns) {
                Matcher matcher = pattern.matcher(body);
                while (matcher.find()) {
                    String res = matcher.group(0);
                    String ver = matcher.group(2);
                    long count = Stream.of(excludes).filter(res::contains).count();
                    if (count == 0L && validator.validate(ver))
                        result.add(new ComparableVersion(ver));
                }
            }
        }
    }

}
