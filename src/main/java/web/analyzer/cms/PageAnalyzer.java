package web.analyzer.cms;

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
    private String responseBody = "";
    private Headers headers;

    public PageAnalyzer prepare(String protocol, String server, List<Boolean> result, String path) {
        this.result = result;

        Host host = new Host(protocol, server, path);
        host.setBegetProtection(true);

        try (Response response = request.send(host)) {
            responseBody = ResponseBodyHandler.readBody(response);
            headers = response.headers();
        }
        return this;
    }

    public void checkViaPageKeywords(Pattern[] patterns) {
        for (Pattern pattern : patterns) {
            parser.configure(pattern, 0);
            if (parser.parse(responseBody)) {
                result.add(true);
                return;
            }
        }
        result.add(false);
    }

    public void checkViaPageCookies(Pattern pattern) {
        List<String> cookies = headers.values("set-cookie");
        parser.configure(pattern, 0);
        for (String cookie : cookies) {
            if (parser.parse(cookie)) {
                result.add(true);
                return;
            }
        }
        result.add(false);
    }
    
}
