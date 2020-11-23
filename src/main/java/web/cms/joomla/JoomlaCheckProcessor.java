package web.cms.joomla;

import com.google.inject.Inject;
import okhttp3.Response;
import web.http.Host;
import web.module.annotation.Get;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.struct.Request;
import web.cms.joomla.annotation.JoomlaCheck;

import java.util.Arrays;
import java.util.Optional;

public class JoomlaCheckProcessor extends AbstractProcessor {

    private final static String successMessage = "  * Joomla tags have been found!";

    private final Request request;
    private final Destination destination;

    private final String[] paths = {
            "administrator"
    };

    private final Integer[] codes = { 200 };

    @Inject
    JoomlaCheckProcessor(@Get Request request,
                         @JoomlaCheck Destination destination) {
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
