package web.http;

import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Objects;

public class ResponseBodyHandler {

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
