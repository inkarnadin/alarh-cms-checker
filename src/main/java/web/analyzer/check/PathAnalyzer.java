package web.analyzer.check;

import lombok.RequiredArgsConstructor;
import okhttp3.Response;
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

    private List<Boolean> result;
    private Host host;

    public PathAnalyzer prepare(String protocol, String server, List<Boolean> result) {
        this.result = result;
        this.host = new Host(protocol, server);
        return this;
    }

    public void checkViaPaths(Integer[] codes, String[] paths) {
        for (String path : paths) {
            host.setPath(path);
            host.setBegetProtection(true);

            try (Response response = request.send(host)) {
                Integer code = response.code();
                if (Arrays.asList(codes).contains(code)) {
                    result.add(true);
                    return;
                }
            }
        }
        result.add(false);
    }

    public void checkViaFiles(Integer[] codes, String[] contentTypes, String[] paths) {
        for (String path : paths) {
            host.setPath(path);
            host.setBegetProtection(true);

            try (Response response = request.send(host)) {
                Integer code = response.code();
                String contentType = ContentType.defineContentType(response.header(CONTENT_TYPE));

                if (Arrays.asList(codes).contains(code) && (Arrays.asList(contentTypes).contains(contentType) || contentTypes.length == 0)) {
                    result.add(true);
                    return;
                }
            }
        }
        result.add(false);
    }

}
