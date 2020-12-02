package web.analyzer.check;

import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import okhttp3.Headers;
import okhttp3.Response;
import web.analyzer.Importance;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.parser.TextParser;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PageAnalyzer {

    private final Request request;
    private final TextParser<Boolean> parser;

    private List<Pair<Boolean, Importance>> result;
    private Host host;

    public PageAnalyzer prepare(String protocol, String server, List<Pair<Boolean, Importance>> result) {
        this.result = result;
        this.host = new Host(protocol, server);
        return this;
    }

    public void checkViaPageKeywords(Importance importance, String[] paths, Pattern[] patterns) {
        for (String path : paths) {
            host.setPath(path);
            host.setBegetProtection(true);
            try (Response response = request.send(host)) {
                String responseBody = ResponseBodyHandler.readBody(response);
                for (Pattern pattern : patterns) {
                    parser.configure(pattern, 0);
                    if (parser.parse(responseBody)) {
                        setResultValue(true, importance);
                        return;
                    }
                }
            }
        }
        setResultValue(false, importance);
    }

    public void checkViaPageCookies(Importance importance, String[] paths, Pattern pattern) {
        for (String path : paths) {
            host.setPath(path);
            host.setBegetProtection(true);
            try (Response response = request.send(host)) {
                Headers headers = response.headers();
                List<String> cookies = headers.values("set-cookie");
                parser.configure(pattern, 0);
                for (String cookie : cookies) {
                    if (parser.parse(cookie)) {
                        setResultValue(true, importance);
                        return;
                    }
                }
            }
        }
        setResultValue(false, importance);
    }

    public void checkViaPageHeaderValues(Importance importance, String path, String[] names, Pattern[] patterns) {
        host.setPath(path);
        try (Response response = request.send(host)) {
            Headers headers = response.headers();

            List<String> choiceHeaders = Arrays.stream(names)
                    .map(headers::values)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            for (Pattern pattern : patterns) {
                parser.configure(pattern, 0);
                for (String hdr : choiceHeaders)
                    if (parser.parse(hdr)) {
                        setResultValue(true, importance);
                        return;
                    }
            }
        }
        setResultValue(false, importance);
    }

    private void setResultValue(boolean resultValue, Importance importance) {
        result.add(new Pair<>(resultValue, importance));
    }
    
}
