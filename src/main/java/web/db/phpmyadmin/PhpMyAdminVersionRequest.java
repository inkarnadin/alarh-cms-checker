package web.db.phpmyadmin;

import okhttp3.*;
import web.AbstractRequest;

public class PhpMyAdminVersionRequest extends AbstractRequest {

    @Override
    public Response send(String... params) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url(params[0] + "://" + params[1] + "/phpmyadmin/doc/html/index.html")
                    .get()
                    .addHeader(USER_AGENT_HEADER, USER_AGENT_HEADER_VALUE)
                    .build();
            return client.newCall(request).execute();
        } catch (Exception xep) {
            return error(xep.getLocalizedMessage());
        }
    }

}
