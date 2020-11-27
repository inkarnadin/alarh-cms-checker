package web.http;

import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static web.http.Charset.UTF8;
import static web.http.Charset.WIN1251;
import static web.http.Headers.CONTENT_TYPE;

public class ResponseBodyHandler {

    @SneakyThrows
    synchronized public static String readBody(Response response) {
        String charset = defineCharset(response.header(CONTENT_TYPE));

        ResponseBody body = response.body();
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(body).byteStream(), charset))) {
            int c = 0;
            while ((c = reader.read()) != -1)
                textBuilder.append((char) c);
        }
        return textBuilder.toString();
    }

    synchronized public static String defineCharset(String contentType) {
        Pattern pattern = Pattern.compile(".*charset=(.*)", Pattern.CASE_INSENSITIVE);
        Matcher mather = pattern.matcher(contentType);

        if (mather.find() && Objects.equals(mather.group(1), WIN1251))
            return WIN1251;

        return UTF8;
    }

}
