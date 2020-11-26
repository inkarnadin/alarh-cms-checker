package web.cms.analyzer.cms;

import lombok.RequiredArgsConstructor;
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

    public PageAnalyzer prepare(String protocol, String server, List<Boolean> result, String path) {
        this.result = result;

        Host host = new Host(protocol, server, path);
        host.setBegetProtection(true);

        try (Response response = request.send(host)) {
            responseBody = ResponseBodyHandler.readBody(response);
        }
        return this;
    }

    public void checkViaPageKeywords(String[] keywords) {
        for (String keyword : keywords) {
            Pattern pattern = Pattern.compile(keyword);
            parser.configure(pattern, 0);
            if (parser.parse(responseBody)) {
                result.add(true);
                return;
            }
        }
        result.add(false);
    }
    
}
