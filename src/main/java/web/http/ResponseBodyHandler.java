package web.http;

import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.Objects;

public class ResponseBodyHandler {

    @SneakyThrows
    synchronized public static String readBody(Response response) {
        ResponseBody body = response.body();
        return (Objects.nonNull(body)) ? body.string() : "";
    }

}
