package web.cms.yii;

import com.google.inject.Inject;
import okhttp3.Response;
import web.cms.yii.annotation.YiiCheck;
import web.http.Host;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.module.annotation.Get;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.struct.Parser;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class YiiCheckProcessor extends AbstractProcessor {

    private final static String successMessage = "  * Yii Framework tags have been found!";

    private final Request request;
    private final Parser parser;
    private final Destination destination;

    private final Integer[] codes = { 200 };

    @Inject
    YiiCheckProcessor(@Get Request request,
                      @YiiCheck Parser parser,
                      @YiiCheck Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        Host host = new Host(protocol, server, null);
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                if (Objects.nonNull(parser.parse(body)))
                    destination.insert(0, successMessage);
            }
        }
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
