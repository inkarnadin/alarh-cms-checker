package web.analyzer.check;

import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.analyzer.Importance;
import web.http.Host;
import web.http.Request;
import web.parser.TextParser;
import web.struct.CreationHeader;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HeaderAnalyzer {

    private final Request request;
    private final TextParser<Boolean> parser;

    private List<Pair<Boolean, Importance>> result;
    private Host host;

    public HeaderAnalyzer prepare(Host host, List<Pair<Boolean, Importance>> result) {
        this.result = result;
        this.host = host;
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
     * Метод поиска определенных значений в заголовоках.
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
     * Метод поиска информации по тегу x-generator.
     */
    public void checkViaSpecialHeader(Importance importance, String[] paths, CreationHeader header, Pattern pattern) {
        for (String path : paths) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String xGenerator = response.headers().get(header.getTag());
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
