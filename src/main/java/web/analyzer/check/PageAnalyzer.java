package web.analyzer.check;

import lombok.RequiredArgsConstructor;
import okhttp3.Headers;
import okhttp3.Response;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.parser.TextParser;

import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class PageAnalyzer {

    private final Request request;
    private final TextParser<Boolean> parser;

    private List<Boolean> result;
    private Host host;

    public PageAnalyzer prepare(String protocol, String server, List<Boolean> result) {
        this.result = result;
        this.host = new Host(protocol, server);
        return this;
    }

    public void checkViaPageKeywords(String[] paths, Pattern[] patterns) {
        for (String path : paths) {
            host.setPath(path);
            host.setBegetProtection(true);
            try (Response response = request.send(host)) {
                String responseBody = ResponseBodyHandler.readBody(response);
                for (Pattern pattern : patterns) {
                    parser.configure(pattern, 0);
                    if (parser.parse(responseBody)) {
                        result.add(true);
                        return;
                    }
                }
            }
        }
        result.add(false);
    }

    public void checkViaPageCookies(String[] paths, Pattern pattern) {
        for (String path : paths) {
            host.setPath(path);
            host.setBegetProtection(true);
            try (Response response = request.send(host)) {
                Headers headers = response.headers();
                List<String> cookies = headers.values("set-cookie");
                parser.configure(pattern, 0);
                for (String cookie : cookies) {
                    if (parser.parse(cookie)) {
                        result.add(true);
                        return;
                    }
                }
            }
        }
        result.add(false);
    }
    
}
