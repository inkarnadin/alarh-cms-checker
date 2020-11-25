package web.cms.wordpress;

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

public class WordPressVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Inject
    WordPressVersionProcessor(@Get Request request,
                              TextParser<String> parser,
                              Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        checkVersionViaPublicMetaInfo();
    }

    private void checkVersionViaPublicMetaInfo() {
        Integer[] codes = { 200 };
        Pattern pattern = Pattern.compile("<meta name=\"generator\".*WordPress\\s(.*?)\" />");

        String version = "unknown";
        Host host = new Host(protocol, server, null);
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                parser.configure(pattern, 1);
                version = parser.parse(body);
            }
        }
        destination.insert(0, String.format("  ** WordPress version (check #1) = %s", version));
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }
}
