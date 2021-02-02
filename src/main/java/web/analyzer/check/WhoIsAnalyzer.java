package web.analyzer.check;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.env.EnvType;
import web.env.whois.WhoisObject;
import web.env.whois.WhoisLocator;
import web.http.Host;
import web.http.Request;
import web.struct.Destination;

import java.util.Objects;

@RequiredArgsConstructor
public class WhoIsAnalyzer {

    private final Request request;
    private final Destination destination;
    private final Host host;

    public void checkWhoIs() {
        destination.insert(0, String.format("  * %s", EnvType.WHOIS.getName()));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Host whoIsHost = WhoisLocator.getAvailableWhoisHost(host.getProtocol(), host.getServer());
            try (Response response = request.send(whoIsHost)) {
                String body = Objects.requireNonNull(response.body()).string();
                WhoisObject dto = objectMapper.readValue(body, WhoisObject.class);

                destination.insert(1, dto.toString());
            }
        } catch (Exception xep) {
            destination.insert(1, xep.getMessage());
        }
    }

}
