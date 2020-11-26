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
public class SpecificAnalyzer {

    private final Request request;
    private final TextParser<Boolean> parser;

    private List<Boolean> result;
    private Host host;

    public SpecificAnalyzer prepare(String protocol, String server, List<Boolean> result) {
        this.result = result;
        this.host = new Host(protocol, server);
        return this;
    }

    public void checkViaError404Message(String path, String[] messages) {
        host.setPath(path);
        try (Response response = request.send(host)) {
            if (response.code() == 404) {
                String body = ResponseBodyHandler.readBody(response);
                for (String message : messages) {
                    Pattern pattern = Pattern.compile(message);
                    parser.configure(pattern, 0);
                    if (parser.parse(body)) {
                        result.add(true);
                        return;
                    }
                }
            }
        }
        result.add(false);
    }

}
