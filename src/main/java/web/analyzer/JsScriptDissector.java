package web.analyzer;

import lombok.experimental.UtilityClass;
import okhttp3.Response;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Компонент разбора и анализа JS-скриптов на странице.
 *
 * @author inkarnadin
 * on 16-01-2021
 */
@UtilityClass
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
     * Метод поиска всех JS-скриптов.
     *
     * @param host    адрес сервера
     * @param request запрос до домашней страницы ресурса
     * @return список найденных скриптов
     */
    public static DissectorResult dissect(Host host, Request request) {
        return dissect(host, request, EMPTY);
    }

    /**
     * Метод поиска только разрешенных JS-скриптов.
     *
     * @param host           адрес сервера
     * @param request        запрос до домашней страницы ресурса
     * @param allowedScripts список скриптов, с которым будет производиться сравнение
     * @return список найденных скриптов
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
     * Метод модификации базового пути до скриптов.
     * <p>Если признак изменения базового пути был обнаружен, происходит проставление соответствующего маркера и обновление
     * всех путей до найденных скриптов.
     *
     * @param results список найденных путей для модификации
     * @param body    тело ответа
     * @return список найденных путей (при необходимости - модифицированных)
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
