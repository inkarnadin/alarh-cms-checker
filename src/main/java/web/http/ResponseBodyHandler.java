package web.http;

import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ResponseBodyHandler {

    @SneakyThrows
    public static String readBody(Response response) {
        if (Objects.isNull(response))
            throw new IllegalArgumentException("Empty response!");

        ResponseBody body = response.body();
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(body).byteStream(),
                Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1)
                textBuilder.append((char) c);
        }
        return textBuilder.toString();
    }

}
