package web.analyzer.check;

import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.analyzer.Importance;
import web.http.ContentType;
import web.http.Host;
import web.http.HttpValidator;
import web.http.Request;

import java.util.Arrays;
import java.util.List;

import static web.http.Headers.CONTENT_TYPE;

@RequiredArgsConstructor
public class PathAnalyzer {

    private final Request request;

    private List<Pair<Boolean, Importance>> result;
    private Host host;

    public PathAnalyzer prepare(String protocol, String server, List<Pair<Boolean, Importance>> result) {
        this.result = result;
        this.host = new Host(protocol, server);
        return this;
    }

    public void checkViaPaths(Importance importance, Integer[] codes, String[] paths) {
        for (String path : paths) {
            host.setPath(path);
            host.setBegetProtection(true);

            try (Response response = request.send(host)) {
                Integer code = response.code();
                if (Arrays.asList(codes).contains(code)) {
                    setResultValue(true, importance);
                    return;
                }
            }
        }
        setResultValue(false, importance);
    }

    public void checkViaFiles(Importance importance, Integer[] codes, String[] contentTypes, String[] paths) {
        for (String path : paths) {
            host.setPath(path);
            host.setBegetProtection(true);

            try (Response response = request.send(host)) {
                Integer code = response.code();
                String contentType = ContentType.defineContentType(response.header(CONTENT_TYPE));

                if (Arrays.asList(codes).contains(code)
                        && (Arrays.asList(contentTypes).contains(contentType) || contentTypes.length == 0)
                        && HttpValidator.isOriginalSource(response, host.getServer())) {
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
