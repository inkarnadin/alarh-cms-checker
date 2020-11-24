package web.cms.joomla;

import com.google.inject.Inject;
import okhttp3.Response;
import web.http.Host;
import web.http.HttpValidator;
import web.http.ResponseBodyHandler;
import web.module.annotation.Get;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.http.Request;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import static web.http.ContentType.APPLICATION_XML;
import static web.http.ContentType.TEXT_XML;
import static web.http.Headers.CONTENT_TYPE;

public class JoomlaCheckProcessor extends AbstractProcessor {

    private final static String successMessage = "  * Joomla tags have been found!";

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Inject
    JoomlaCheckProcessor(@Get Request request,
                         TextParser<Boolean> parser,
                         Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        if (checkViaSpecifyPaths() || checkViaMainPage() || checkViaSpecifyFiles())
            destination.insert(0, successMessage);
    }

    private boolean checkViaSpecifyPaths() {
        String[] paths = { "administrator/components" };
        Integer[] codes = { 200, 304, 401 };

        for (String path : paths) {
            Host host = new Host(protocol, server, path);
            host.setBegetProtection(true);

            try (Response response = request.send(host)) {
                Integer code = response.code();
                if (Arrays.asList(codes).contains(code) && !HttpValidator.isRedirect(response)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkViaMainPage() {
        Integer[] codes = { 200 };
        Pattern pattern = Pattern.compile("<meta name=\"generator\" content=\"(Joomla!).*/>");

        Host host = new Host(protocol, server, null);
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                parser.configure(pattern, 0);
                return parser.parse(body);
            }
        }
        return false;
    }

    private boolean checkViaSpecifyFiles() {
        Integer[] codes = { 304 };
        String[] contentTypes = { TEXT_XML, APPLICATION_XML };

        Host host = new Host(protocol, server, "administrator/manifests/files/joomla.xml");
        try (Response response = request.send(host)) {
            Integer code = response.code();
            String contentType = response.header(CONTENT_TYPE);

            if (Arrays.asList(codes).contains(code) && Arrays.asList(contentTypes).contains(contentType))
                return true;
        }
        return false;
    }


    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
