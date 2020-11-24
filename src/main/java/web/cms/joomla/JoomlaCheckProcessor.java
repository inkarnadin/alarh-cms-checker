package web.cms.joomla;

import com.google.inject.Inject;
import okhttp3.Response;
import web.http.Host;
import web.http.HttpValidator;
import web.http.ResponseBodyHandler;
import web.module.annotation.Get;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.http.Request;
import web.cms.joomla.annotation.JoomlaCheck;
import web.struct.Parser;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class JoomlaCheckProcessor extends AbstractProcessor {

    private final static String successMessage = "  * Joomla tags have been found!";

    private final Request request;
    private final Parser parser;
    private final Destination destination;

    private final String[] paths = {
            "administrator"
    };

    private final Integer[] codes = { 200, 401 };

    @Inject
    JoomlaCheckProcessor(@Get Request request,
                         @JoomlaCheck Parser parser,
                         @JoomlaCheck Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        if (checkViaSpecifyPaths() || checkViaMainPage())
            destination.insert(0, successMessage);
    }

    private boolean checkViaSpecifyPaths() {
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
        Host host = new Host(protocol, server, null);
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                return Objects.nonNull(parser.parse(body));
            }
        }
        return false;
    }


    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
