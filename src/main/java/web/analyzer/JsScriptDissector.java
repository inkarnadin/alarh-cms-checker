package web.analyzer;

import okhttp3.Response;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsScriptDissector {

    private static final String[] excludedList = {
            "yandex",
            "google",
            "twitter",
            "facebook",
            "http",
            "https",
            "//"
    };

    private static final String[] EMPTY = {};

    /**
     * Find all javascript file
     *
     * @param host    server
     * @param request request execution class
     * @return list of found scripts
     */
    public static DissectorResult dissect(Host host, Request request) {
        return dissect(host, request, EMPTY);
    }

    /**
     * Find certain javascript file
     *
     * @param host           server
     * @param request        request execution class
     * @param allowedScripts sought scripts
     * @return result of dissect
     */
    public static DissectorResult dissect(Host host, Request request, String[] allowedScripts) {
        Response response = request.send(host);
        String body = ResponseBodyHandler.readBody(response);

        List<String> results = new ArrayList<>();

        Matcher matcher = Pattern.compile("<script (type=\"text/javascript\" )?src=\"/?(.*?)[\"?]").matcher(body);
        while (matcher.find()) {
            String result = matcher.group(2);

            boolean isExcluded = false;
            for (String exclude : excludedList) {
                if (result.contains(exclude) && !result.contains(host.getServer())) {
                    isExcluded = true;
                    break;
                }
            }

            boolean isAllowed = allowedScripts.length == 0;
            for (String allow : allowedScripts) {
                if (result.contains(allow)) {
                    isAllowed = true;
                    break;
                }
            }

            boolean isJsFile = result.contains("js");
            if (!isExcluded && isAllowed && isJsFile) {
                results.add(result);
            }
        }

        return modifyBasePath(results, body);
    }

    /**
     * Modify base path if it was overwritten.
     * <p>If base path changed, update all paths with it changes.
     *
     * @param results result of paths
     * @param body    page response body
     * @return result of dissect with overwritten paths if needed
     */
    private static DissectorResult modifyBasePath(List<String> results, String body) {
        Matcher basePathMatcher = Pattern.compile("<base href=\"(//)?(.*?)/\">").matcher(body);
        String basePath = null;
        while (basePathMatcher.find()) {
            basePath = basePathMatcher.group(2);
        }

        List<String> modifyResults = new ArrayList<>();
        if (Objects.nonNull(basePath)) {
            for (String path : results) {
                modifyResults.add(path.replace("./", basePath + "/"));
            }
            return DissectorResult.builder()
                    .isOverWrittenBasePath(true)
                    .paths(modifyResults.toArray(new String[0]))
                    .build();
        }
        return DissectorResult.builder()
                .isOverWrittenBasePath(false)
                .paths(results.toArray(new String[0]))
                .build();
    }

}
