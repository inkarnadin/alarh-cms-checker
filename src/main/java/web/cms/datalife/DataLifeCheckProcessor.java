package web.cms.datalife;

import com.google.inject.Inject;
import okhttp3.Response;
import web.cms.datalife.annotation.DataLifeCheck;
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

public class DataLifeCheckProcessor extends AbstractProcessor {

    private final static String successMessage = "  * DataLife Engine tags have been found!";

    private final Request request;
    private final Parser parser;
    private final Destination destination;

    @Inject
    DataLifeCheckProcessor(@Get Request request,
                           @DataLifeCheck Parser parser,
                           @DataLifeCheck Destination destination) {
        this.request = request;
        this.parser = parser;
        this.destination = destination;
    }

    @Override
    public void process() {
        if (checkViaMainPage())
            destination.insert(0, successMessage);
    }

    private boolean checkViaMainPage() {
        Integer[] codes = { 200 };

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
