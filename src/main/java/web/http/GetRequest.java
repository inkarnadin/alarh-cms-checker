package web.http;

import com.google.inject.Inject;
import okhttp3.Request;
import okhttp3.Response;

import static web.http.Headers.COOKIE;
import static web.http.Headers.USER_AGENT_HEADER;

public class GetRequest extends AbstractRequest {

    @Inject
    Client client;

    @Override
    public Response send(Host host) {
        try {
            Request.Builder builder = new Request.Builder()
                    .url(host.createUrl())
                    .get()
                    .addHeader(USER_AGENT_HEADER, USER_AGENT_HEADER_VALUE);

            headers.forEach(builder::addHeader);

            if (host.isBegetProtection())
                builder.addHeader(COOKIE, BEGET_PROTECTION_COOKIE);

            return client.execute(builder.build());
        } catch (Exception xep) {
            return error(xep.getLocalizedMessage(), host);
        }
    }

}
