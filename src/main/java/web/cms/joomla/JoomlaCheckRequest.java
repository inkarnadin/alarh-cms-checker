package web.cms.joomla;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import web.struct.AbstractRequest;

public class JoomlaCheckRequest extends AbstractRequest {

    @Override
    public Response send(String... params) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(params[0] + "://" + params[1] + "/" + params[2])
                    .get()
                    .addHeader(USER_AGENT_HEADER, USER_AGENT_HEADER_VALUE)
                    .build();
            return client.newCall(request).execute();
        } catch (Exception xep) {
            return error(xep.getLocalizedMessage());
        }
    }

}
