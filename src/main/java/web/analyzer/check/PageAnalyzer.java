package web.analyzer.check;

import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.analyzer.Importance;
import web.http.ContentType;
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

    private List<Pair<Boolean, Importance>> result;
    private Host host;

    public PageAnalyzer prepare(Host host, List<Pair<Boolean, Importance>> result) {
        this.result = result;
        this.host = host;
        return this;
    }

    /**
     * Check via special keywords on a page
     *
     * @param importance - importance level of check
     * @param paths - list of checking paths
     * @param patterns - list of finding patterns
     */
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

    /**
     * Special case of <code>checkViaPageKeywords</code> - checking via robots.txt
     *
     * @param importance - importance level of check
     * @param patterns - list of finding patterns
     */
    public void checkViaRobots(Importance importance, Pattern[] patterns) {
        host.setPath("robots.txt");
        try (Response response = request.send(host)) {
            String responseBody = ResponseBodyHandler.readBody(response);
            String contentType = ContentType.defineContentType(response.header("Content-Type"));

            for (Pattern pattern : patterns) {
                parser.configure(pattern, 0);
                if (parser.parse(responseBody) && response.code() != 404 && contentType.equals(ContentType.TEXT_PLAIN)) {
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
