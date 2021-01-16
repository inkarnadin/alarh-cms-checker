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
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class MainPageAnalyzer {

    private final Request request;
    private final TextParser<Boolean> parser;

    private List<Pair<Boolean, Importance>> result;
    private String responseBody = "";

    public MainPageAnalyzer prepare(Host host, List<Pair<Boolean, Importance>> result) {
       this.result = result;
       try (Response response = request.send(host)) {
           responseBody = ResponseBodyHandler.readBody(response);
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

    public void checkViaMainPageMetaTag(Importance importance, String[] keywords) {
        for (String keyword : keywords) {
            Pattern pattern = Pattern.compile(String.format("<meta name=\"(%s)\" content", keyword));
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

    private void setResultValue(boolean resultValue, Importance importance) {
        result.add(new Pair<>(resultValue, importance));
    }

}
