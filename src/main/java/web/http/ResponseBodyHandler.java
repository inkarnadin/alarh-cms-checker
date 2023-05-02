package web.http;

import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Objects;

/**
 * Компонент обработки тела запроса.
 *
 * @author inkarnadin
 * on 24-11-2020
 */
public class ResponseBodyHandler {

    /**
     * Метод чтения тела запроса. В случае ошибки возвращает пустое тело.
     *
     * @param response http ответ
     * @return тело запроса
     */
    synchronized public static String readBody(Response response) {
        try {
            ResponseBody body = response.body();
            return (Objects.nonNull(body)) ? body.string() : "";
        } catch (IOException xep) {
            System.out.println("Read response exception");
            return "";
        }
    }

}
