package web.cms.datalife;

import com.google.inject.Inject;
import okhttp3.Response;
import web.cms.CMSType;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.module.annotation.Get;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.parser.TextParser;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.http.ContentType.*;
import static web.http.Headers.CONTENT_TYPE;

public class DataLifeCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Inject
    DataLifeCheckProcessor(@Get Request request,
                           TextParser<Boolean> parser,
                           Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        checkViaMainPage();
        checkViaAdminPage();
        checkViaSpecifyScriptName();
        checkViaLogoPath();
        checkViaError404Message();

        if (successAttempt.get() > 0)
            destination.insert(0, String.format(successMessage, CMSType.DATALIFE_ENGINE.getName(), successAttempt, attempt));
    }

    private void checkViaMainPage() {
        Integer[] codes = { 200 };
        Pattern pattern = Pattern.compile("<meta name=\"generator\" content=\"(DataLife Engine).*");

        attempt.incrementAndGet();

        Host host = new Host(protocol, server, null);
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                parser.configure(pattern, 0);
                if (parser.parse(body))
                    successAttempt.incrementAndGet();
            }
        }
    }

    private void checkViaSpecifyScriptName() {
        Integer[] codes = { 200 };
        Pattern pattern = Pattern.compile("engine/classes/js/dle_js\\.js");

        attempt.incrementAndGet();

        Host host = new Host(protocol, server, null);
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                parser.configure(pattern, 0);
                if (parser.parse(body))
                    successAttempt.incrementAndGet();
            }
        }
    }

    private void checkViaLogoPath() {
        Integer[] codes = { 200, 304 };
        String[] contentTypes = { IMAGE_JPG, IMAGE_PNG };
        String[] paths = {
                "engine/skins/images/logos.jpg",
                "engine/skins/images/logo.png" };

        attempt.incrementAndGet();

        for (String path : paths) {
            Host host = new Host(protocol, server, path);
            try (Response response = request.send(host)) {
                Integer code = response.code();
                String contentType = response.header(CONTENT_TYPE);
                if (Arrays.asList(codes).contains(code) && Arrays.asList(contentTypes).contains(contentType)) {
                    successAttempt.incrementAndGet();
                    return;
                }
            }
        }
    }

    private void checkViaAdminPage() {
        Integer[] codes = { 200 };
        Pattern pattern = Pattern.compile("DataLife Engine");

        attempt.incrementAndGet();

        Host host = new Host(protocol, server, "admin.php");
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                parser.configure(pattern, 0);
                if (parser.parse(body))
                    successAttempt.incrementAndGet();
            }
        }
    }

    private void checkViaError404Message() {
        Integer[] codes = { 404 };
        String[] messages = {
                "По данному адресу публикаций на сайте не найдено, либо у Вас нет доступа для просмотра информации по данному адресу"
        };

        attempt.incrementAndGet();

        Host host = new Host(protocol, server, "administrator");
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                for (String message : messages) {
                    Pattern pattern = Pattern.compile(message, Pattern.CASE_INSENSITIVE);
                    parser.configure(pattern, 0);
                    if (parser.parse(body)) {
                        successAttempt.incrementAndGet();
                        return;
                    }
                }
            }
        }
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
