package web.db.phpmyadmin;

import com.google.inject.Inject;
import lombok.SneakyThrows;
import okhttp3.Response;
import web.http.Host;
import web.http.ResponseBodyHandler;
import web.module.annotation.Get;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;

import java.util.Arrays;
import java.util.regex.Pattern;

public class PhpMyAdminVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<String> parser;

    @Inject
    PhpMyAdminVersionProcessor(@Get Request request,
                               TextParser<String> parser) {
        this.request = request;
        this.parser = parser;
    }

    @Override
    @SneakyThrows
    public void process() {
        Integer[] codes = { 200 };
        String[] paths = {
                "phpmyadmin/doc/html/index.html",
                "phpmyadmin/Documentation.html"
        };
        Pattern pattern = Pattern.compile("<title>.*phpMyAdmin\\s(.*?)\\s");

        for (String path : paths) {
            Host host = new Host(protocol, server, path);
            try (Response response = request.send(host)) {
                Integer code = response.code();

                if (Arrays.asList(codes).contains(code)) {
                    String body = ResponseBodyHandler.readBody(response);
                    parser.configure(pattern, 1);
                    String version = parser.parse(body);

                    System.out.println("PhpMyAdmin version = " + version);
                    return;
                }

            }
        }
    }

}
