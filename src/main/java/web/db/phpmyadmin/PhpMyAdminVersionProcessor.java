package web.db.phpmyadmin;

import com.google.inject.Inject;
import lombok.SneakyThrows;
import okhttp3.Response;
import web.db.phpmyadmin.annotation.PhpMyAdminVersion;
import web.http.Host;
import web.http.ResponseBodyHandler;
import web.module.annotation.Get;
import web.http.Request;
import web.struct.Parser;
import web.struct.AbstractProcessor;

public class PhpMyAdminVersionProcessor extends AbstractProcessor {

    private final String path = "/phpmyadmin/doc/html/index.html";

    private final Request request;
    private final Parser parser;

    @Inject
    PhpMyAdminVersionProcessor(@Get Request request,
                               @PhpMyAdminVersion Parser parser) {
        this.request = request;
        this.parser = parser;
    }

    @Override
    @SneakyThrows
    public void process() {
        Host host = new Host(protocol, server, path);
        try (Response response = request.send(host)) {
            String body = ResponseBodyHandler.readBody(response);
            String version = parser.parse(body);

            System.out.println("PhpMyAdmin version = " + version);
        }
    }

}
