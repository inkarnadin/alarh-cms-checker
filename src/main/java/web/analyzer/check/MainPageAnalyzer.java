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

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class MainPageAnalyzer {

    private final Request request;
    private final TextParser<Boolean> parser;

    private List<Pair<Boolean, Importance>> result;
    private String responseBody = "";
    private Headers headers;

    public MainPageAnalyzer prepare(String protocol, String server, List<Pair<Boolean, Importance>> result) {
       this.result = result;

       Host host = new Host(protocol, server);
       try (Response response = request.send(host)) {
           responseBody = ResponseBodyHandler.readBody(response);
           headers = response.headers();
       }
       return this;
    }

    public void checkViaMainPageGenerator(Importance importance, String[] keywords) {
        for (String keyword : keywords) {
            Pattern pattern = Pattern.compile(String.format("<meta name=\"[gG]enerator\" content=\"(%s).*", keyword));
            parser.configure(pattern, 0);
            if (parser.parse(responseBody)) {
                setResultValue(true, importance);
                return;
            }
        }
        setResultValue(false, importance);
    }

    public void checkViaMainPageKeywords(Importance importance, Pattern[] patterns) {
        for (Pattern pattern : patterns) {
            parser.configure(pattern, 0);
            if (parser.parse(responseBody)) {
                setResultValue(true, importance);
                return;
            }
        }
        setResultValue(false, importance);
    }

    public void checkViaMainPageScriptName(Importance importance, Pattern[] patterns) {
        for (Pattern pattern : patterns) {
            parser.configure(pattern, 0);
            if (parser.parse(responseBody)) {
                setResultValue(true, importance);
                return;
            }
        }
        setResultValue(false, importance);
    }

    public void checkViaMainPageHeaders(Importance importance, String[] names) {
        for (String name : names) {
            if (Objects.nonNull(headers.get(name))) {
                setResultValue(true, importance);
                return;
            }
        }
        setResultValue(false, importance);
    }

    public void checkViaMainPageXGeneratorHeader(Importance importance, Pattern pattern) {
        String header = headers.get("x-generator");
        if (Objects.nonNull(header)) {
            parser.configure(pattern, 0);
            if (parser.parse(header.toLowerCase())) {
                setResultValue(true, importance);
                return;
            }
        }
        setResultValue(false, importance);
    }

    private void setResultValue(boolean resultValue, Importance importance) {
        result.add(new Pair<>(resultValue, importance));
    }

}
