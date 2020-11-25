package web.cms.datalife;

import com.google.inject.Inject;
import okhttp3.Response;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.module.annotation.Get;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public class DataLifeVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<String> textParser;
    private final Destination destination;

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
    }

    private void checkVersionViaSpecifyFiles() {
        String version = "unknown";
        Integer[] codes = { 200 };

        Pattern pattern = Pattern.compile("Актуальная версия скрипта: (.*)");

        Host host = new Host(protocol, server, null);
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

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
