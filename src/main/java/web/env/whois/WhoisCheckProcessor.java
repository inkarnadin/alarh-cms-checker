package web.env.whois;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.ResponseBody;
import web.env.AbstractEnvironmentProcessor;
import web.http.Host;
import web.http.Request;
import web.struct.Destination;

import java.util.Objects;

@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class WhoisCheckProcessor extends AbstractEnvironmentProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Request request;
    private final Destination destination;

    @Override
    @SneakyThrows
    public void process() {
        Host host = WhoisLocator.getAvailableWhoisHost(protocol, server);
        try (Response response = request.send(host)) {
            String body = Objects.requireNonNull(response.body()).string();
            WhoisDto dto = objectMapper.readValue(body, WhoisDto.class);
            //System.out.println(dto);
        }
    }

}
