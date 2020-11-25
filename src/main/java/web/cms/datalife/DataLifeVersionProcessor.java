package web.cms.datalife;

import com.google.inject.Inject;
import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.ResponseBody;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.module.annotation.Get;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.http.ContentType.IMAGE_JPG;
import static web.http.ContentType.IMAGE_PNG;
import static web.http.Headers.CONTENT_TYPE;

public class DataLifeVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<String> textParser;
    private final Destination destination;

    private final DataLifeLogoMap logoMap = new DataLifeLogoMap();

    @Inject
    DataLifeVersionProcessor(@Get Request request,
                             TextParser<String> textParser,
                             Destination destination) {
        this.request = request;
        this.textParser = textParser;
        this.destination = destination;
    }

    @Override
    public void process() {
        checkVersionViaSpecifyFiles();
        checkViaLogo();
    }

    private void checkVersionViaSpecifyFiles() {
        String version = "unknown";
        Integer[] codes = { 200 };

        Pattern pattern = Pattern.compile("Актуальная версия скрипта: (.*)");

        Host host = new Host(protocol, server, "/engine/ajax/updates.php");
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                textParser.configure(pattern, 1);
                version = textParser.parse(body);
            }
        }
        destination.insert(0, String.format("  ** DataLifeEngine version (check #1) = %s", version));
    }

    @SneakyThrows
    private void checkViaLogo() {
        String version = "unknown";
        Integer[] codes = { 200, 304 };
        String[] contentTypes = { IMAGE_JPG, IMAGE_PNG };
        String[] paths = {
                "engine/skins/images/logos.jpg",
                "engine/skins/images/logo.png",
                "templates/Default/images/logotype.png",
                "templates/Default/images/logo.png"
        };

        attempt.incrementAndGet();

        for (String path : paths) {
            Host host = new Host(protocol, server, path);
            try (Response response = request.send(host)) {
                Integer code = response.code();
                String contentType = response.header(CONTENT_TYPE);
                if (Arrays.asList(codes).contains(code) && Arrays.asList(contentTypes).contains(contentType)) {
                    ResponseBody body = response.body();
                    long contentLength = (Objects.nonNull(body)) ? body.contentLength() : 0;

                    version = logoMap.getVersion(contentLength);
                }
            }
        }
        destination.insert(1, String.format("  ** DataLifeEngine version (check #2) = %s", version));
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
