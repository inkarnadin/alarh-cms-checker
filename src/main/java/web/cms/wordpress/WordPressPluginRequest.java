package web.cms.wordpress;

import okhttp3.*;
import web.struct.AbstractRequest;

public class WordPressPluginRequest extends AbstractRequest {

    @Override
    public Response send(String... params) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(params[0] + "://" + params[1] + "/wp-content/plugins/" + params[2])
                    .get()
                    .addHeader(USER_AGENT_HEADER, USER_AGENT_HEADER_VALUE)
                    .build();
            return client.newCall(request).execute();
        } catch (Exception xep) {
            return error(xep.getLocalizedMessage());
        }
    }

}
