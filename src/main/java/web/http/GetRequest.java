package web.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static web.http.Headers.USER_AGENT_HEADER;

public class GetRequest extends AbstractRequest {

    @Override
    public Response send(Host host) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(host.createUrl())
                    .get()
                    .addHeader(USER_AGENT_HEADER, USER_AGENT_HEADER_VALUE)
                    .build();
            return client.newCall(request).execute();
        } catch (Exception xep) {
            return error(xep.getLocalizedMessage());
        }
    }

}
