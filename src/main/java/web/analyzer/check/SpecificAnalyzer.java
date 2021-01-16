package web.analyzer.check;

import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.analyzer.Importance;
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

    private List<Pair<Boolean, Importance>> result;
    private Host host;

    public SpecificAnalyzer prepare(Host host, List<Pair<Boolean, Importance>>  result) {
        this.result = result;
        this.host = host;
        return this;
    }

    public void checkViaError404Message(Importance importance, String path, String[] messages) {
        host.setPath(path);
        try (Response response = request.send(host)) {
            if (response.code() == 404) {
                String body = ResponseBodyHandler.readBody(response);
                for (String message : messages) {
                    Pattern pattern = Pattern.compile(message);
                    parser.configure(pattern, 0);
                    if (parser.parse(body)) {
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
