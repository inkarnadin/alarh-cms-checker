package web.cms.wordpress;

import com.google.inject.Inject;
import okhttp3.Response;
import web.http.Host;
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

    private final String[] paths = {
            "wp-content",
            "wp-admin"
    };

    private final Integer[] codes = { 200 };

    @Inject
    WordPressCheckProcessor(@Get Request request,
                            @WordPressCheck Destination destination) {
        this.request = request;
        this.destination = destination;
    }

    @Override
    public void process() {
        for (String path : paths) {
            Host host = new Host(protocol, server, path);
            try (Response response = request.send(host)) {
                Integer code = response.code();
                if (Arrays.asList(codes).contains(code)) {
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
