package web.cms.wordpress;

import com.google.inject.Inject;
import okhttp3.Response;
import web.http.Host;
import web.http.HttpValidator;
import web.module.annotation.Get;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.http.Request;
import web.cms.wordpress.annotation.WordPressCheck;

import java.util.Arrays;
import java.util.Optional;

public class WordPressCheckProcessor extends AbstractProcessor {

    private final static String successMessage = "  * WordPress tags have been found!";

    private final Request request;
    private final Destination destination;

    @Inject
    WordPressCheckProcessor(@Get Request request,
                            @WordPressCheck Destination destination) {
        this.request = request;
        this.destination = destination;
    }

    @Override
    public void process() {
        String[] paths = { "wp-content", "wp-admin" };
        Integer[] codes = { 200, 403 };

        for (String path : paths) {
            Host host = new Host(protocol, server, path);
            host.setBegetProtection(true);

            try (Response response = request.send(host)) {
                Integer code = response.code();
                if (Arrays.asList(codes).contains(code) && !HttpValidator.isRedirect(response)) {
                    destination.insert(0, successMessage);
                    return;
                }
            }
        }
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
