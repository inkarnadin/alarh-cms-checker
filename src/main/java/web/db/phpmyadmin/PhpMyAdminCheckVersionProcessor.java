package web.db.phpmyadmin;

import com.google.inject.Inject;
import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.ResponseBody;
import web.http.Host;
import web.module.annotation.Get;
import web.struct.Request;
import web.struct.AbstractProcessor;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhpMyAdminCheckVersionProcessor extends AbstractProcessor {

    private final String path = "/phpmyadmin/doc/html/index.html";

    private final Request request;

    @Inject
    PhpMyAdminCheckVersionProcessor(@Get Request request) {
        this.request = request;
    }

    @Override
    @SneakyThrows
    public void process() {
        Host host = new Host(protocol, server, path);
        try (Response response = request.send(host)) {
            ResponseBody body = response.body();
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(body).byteStream(),
                    Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1)
                    textBuilder.append((char) c);
            }
            Pattern pattern = Pattern.compile("<title>.*phpMyAdmin\\s(.*)\\s.*</title>");
            Matcher matcher = pattern.matcher(textBuilder.toString());

            String version = matcher.find() ? matcher.group(1) : "???";
            System.out.println("PhpMyAdmin Version = " + version);
        }
    }

}
