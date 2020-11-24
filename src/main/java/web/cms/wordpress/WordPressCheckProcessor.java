package web.cms.wordpress;

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
import java.util.Optional;
import java.util.regex.Pattern;

public class WordPressCheckProcessor extends AbstractProcessor {

    private final static String successMessage = "  * WordPress tags have been found!";

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Inject
    WordPressCheckProcessor(@Get Request request,
                            TextParser<Boolean> parser,
                            Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        if (checkViaMainPage() || checkViaSpecifyPaths())
        destination.insert(0, successMessage);
    }

    private boolean checkViaMainPage() {
        Integer[] codes = { 200 };
        Pattern pattern = Pattern.compile("<meta name=\"generator\" content=\"(WordPress).*/>");

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

    private boolean checkViaSpecifyPaths() {
        String[] paths = { "wp-content", "wp-admin" };
        Integer[] codes = { 200, 403 };

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

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
