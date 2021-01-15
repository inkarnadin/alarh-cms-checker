package web.analyzer.check;

import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.analyzer.Importance;
import web.http.Host;
import web.http.Request;
import web.parser.TextParser;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HeaderAnalyzer {

    private final Request request;
    private final TextParser<Boolean> parser;

    private List<Pair<Boolean, Importance>> result;
    private Host host;

    public HeaderAnalyzer prepare(String protocol, String server, List<Pair<Boolean, Importance>> result) {
        this.result = result;
        this.host = new Host(protocol, server);
        host.setBegetProtection(true);

        return this;
    }

    /**
     * Find certain headers
     */
    public void checkViaHeaders(Importance importance, String[] paths, String[] names) {
        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                for (String name : names) {
                    if (Objects.nonNull(response.headers().get(name))) {
                        setResultValue(true, importance);
                        return;
                    }
                }
            }
        }
        setResultValue(false, importance);
    }

    /**
     * Find certain values in headers
     */
    public void checkViaHeaderValues(Importance importance, String[] paths, Pattern[] patterns) {
        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String unionHeaderValues = response.headers().toMultimap().values().stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.joining());
                for (Pattern pattern : patterns) {
                    parser.configure(pattern, 0);
                    if (parser.parse(unionHeaderValues)) {
                        setResultValue(true, importance);
                        return;
                    }
                }
            }
        }
        setResultValue(false, importance);
    }

    /**
     * Find info in x-generator
     */
    public void checkViaXGenerator(Importance importance, String[] paths, Pattern pattern) {
        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String xGenerator = response.headers().get("x-generator");
                if (Objects.nonNull(xGenerator)) {
                    parser.configure(pattern, 0);
                    if (parser.parse(xGenerator.toLowerCase())) {
                        setResultValue(true, importance);
                        return;
                    }
                }
            }
        }
        setResultValue(false, importance);
    }

    /**
     * Find certain value in cookies
     */
    public void checkViaCookies(Importance importance, String[] paths, Pattern[] patterns) {
        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String cookies = String.join("", response.headers().values("set-cookie"));
                for (Pattern pattern : patterns) {
                    parser.configure(pattern, 0);
                    if (parser.parse(cookies)) {
                        setResultValue(true, importance);
                        return;
                    }
                }
            }
        }
        setResultValue(false, importance);
    }

    private void setResultValue(boolean resultValue, Importance importance) {
        result.add(new Pair<>(resultValue, importance));
    }

}
