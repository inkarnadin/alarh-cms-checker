package web.cms.joomla;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import okhttp3.Response;
import web.cms.joomla.annotation.JoomlaVersion;
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

import static web.http.ContentType.TEXT_XML;
import static web.http.Headers.CONTENT_TYPE;

public class JoomlaVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final Parser firstParser;
    private final Parser secondParser;
    private final Destination destination;

    private final Integer[] codes = { 200 };

    @Inject
    JoomlaVersionProcessor(@Get Request request,
                           @Named("ViaLangPackage") Parser firstParser,
                           @Named("ViaPublicMetaInf") Parser secondParser,
                           @JoomlaVersion Destination destination) {
        this.request = request;
        this.firstParser = firstParser;
        this.secondParser = secondParser;
        this.destination = destination;
    }

    @Override
    public void process() {
        checkVersionViaLangConfig();
        checkVersionViaPublicMetaInfo();
        checkVersionViaConfigXml();
    }

    private void checkVersionViaLangConfig() {
        String version = "unknown";
        Host host = new Host(protocol, server, "/language/en-GB/en-GB.xml");
        try (Response response = request.send(host)) {
            Integer code = response.code();
            String contentType = response.header(CONTENT_TYPE);

            if (Arrays.asList(codes).contains(code) && Objects.equals(contentType, TEXT_XML)) {
                String body = ResponseBodyHandler.readBody(response);
                version = firstParser.parse(body);
            }
        }
        destination.insert(0, String.format("  ** Joomla version (check #1) = %s", version));
    }

    private void checkVersionViaPublicMetaInfo() {
        String version = "unknown";
        Host host = new Host(protocol, server, null);
        try (Response response = request.send(host)) {
            Integer code = response.code();

            if (Arrays.asList(codes).contains(code)) {
                String body = ResponseBodyHandler.readBody(response);
                version = secondParser.parse(body);
            }
        }
        destination.insert(1, String.format("  ** Joomla version (check #1) = %s", version));
    }

    private void checkVersionViaConfigXml() {
        String version = "unknown";
        Host host = new Host(protocol, server, "administrator/components/com_config/config.xml");
        try (Response response = request.send(host)) {
            Integer code = response.code();
            String contentType = response.header(CONTENT_TYPE);

            if (Arrays.asList(codes).contains(code) && Objects.equals(contentType, TEXT_XML)) {
                String body = ResponseBodyHandler.readBody(response);
                version = firstParser.parse(body);
            }
        }
        destination.insert(2, String.format("  ** Joomla version (check #3) = %s", version));
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
