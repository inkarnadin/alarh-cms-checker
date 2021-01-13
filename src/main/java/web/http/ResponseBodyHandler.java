package web.http;

import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

import static web.http.Charset.*;
import static web.http.Headers.CONTENT_TYPE;

public class ResponseBodyHandler {

    @SneakyThrows
    synchronized public static String readBody(Response response) {
        String charset = defineCharset(response.header(CONTENT_TYPE));

        ResponseBody body = response.body();
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(body).byteStream(), charset))) {
            try {
                int c = 0;
                while ((c = reader.read()) != -1)
                    textBuilder.append((char) c);
            } catch (Exception e) {
                System.out.println(e.getMessage() + ": " + body.toString());
            }
        }
        return textBuilder.toString();
    }

}
