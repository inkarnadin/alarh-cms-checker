package web.cms.datalife;

import com.google.inject.Inject;
import okhttp3.Response;
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

public class DataLifeCheckProcessor extends AbstractProcessor {

    private final static String successMessage = "  * DataLife Engine tags have been found!";

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
        if (checkViaMainPage() || checkViaAdminPage())
            destination.insert(0, successMessage);
    }

    private boolean checkViaMainPage() {
        Integer[] codes = { 200 };
        Pattern pattern = Pattern.compile("<meta name=\"generator\" content=\"(DataLife Engine).*");

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

    private boolean checkViaAdminPage() {
        Integer[] codes = { 200 };
        Pattern pattern = Pattern.compile("DataLife Engine");

        Host host = new Host(protocol, server, "admin.php");
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

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
