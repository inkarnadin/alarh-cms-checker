package web.cms.analyzer.cms;

import lombok.RequiredArgsConstructor;
import okhttp3.Headers;
import okhttp3.Response;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.parser.TextParser;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class MainPageAnalyzer {

    private final Request request;
    private final TextParser<Boolean> parser;

    private List<Boolean> result;
    private String responseBody = "";
    private Headers headers;

    public MainPageAnalyzer prepare(String protocol, String server, List<Boolean> result) {
       this.result = result;

       Host host = new Host(protocol, server);
       try (Response response = request.send(host)) {
           responseBody = ResponseBodyHandler.readBody(response);
           headers = response.headers();
       }
       return this;
    }

    public void checkViaMainPageGenerator(String[] keywords) {
        for (String keyword : keywords) {
            Pattern pattern = Pattern.compile(String.format("<meta name=\"[gG]enerator\" content=\"(%s).*", keyword));
            parser.configure(pattern, 0);
            if (parser.parse(responseBody)) {
                result.add(true);
                return;
            }
        }
        result.add(false);
    }

    public void checkViaMainPageKeywords(Pattern[] patterns) {
        for (Pattern pattern : patterns) {
            parser.configure(pattern, 0);
            if (parser.parse(responseBody)) {
                result.add(true);
                return;
            }
        }
        result.add(false);
    }

    public void checkViaMainPageScriptName(Pattern[] patterns) {
        for (Pattern pattern : patterns) {
            parser.configure(pattern, 0);
            if (parser.parse(responseBody)) {
                result.add(true);
                return;
            }
        }
        result.add(false);
    }

    public void checkViaMainPageHeaders(String[] names) {
        for (String name : names) {
            if (Objects.nonNull(headers.get(name))) {
                result.add(true);
                return;
            }
            result.add(false);
        }
    }

    public void checkViaMainPageXGeneratorHeader(String value) {
        String header = headers.get("x-generator");
        if (Objects.nonNull(header)) {
            result.add(header.toLowerCase().matches(value.toLowerCase()));
            return;
        }
        result.add(false);
    }

}
