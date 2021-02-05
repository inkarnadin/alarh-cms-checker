package web.http;

import com.google.inject.Inject;
import okhttp3.Request;
import okhttp3.Response;

public class HeadRequest extends AbstractRequest {

    @Inject
    Client client;

    @Override
    public Response send(Host host) {
        okhttp3.Request.Builder builder = new Request.Builder()
                .url(host.createUrl())
                .head();
        return client.execute(builder.build());
    }

}
