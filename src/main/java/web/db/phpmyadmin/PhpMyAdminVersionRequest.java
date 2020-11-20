package web.db.phpmyadmin;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import web.cms.AbstractRequest;

public class PhpMyAdminVersionRequest extends AbstractRequest {

    @Override
    @SneakyThrows
    public Response send(String... params) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(params[0] + "://" + params[1] + "/phpmyadmin/doc/html/index.html")
                .method(GET, null)
                .addHeader(USER_AGENT_HEADER, USER_AGENT_HEADER_VALUE)
                .build();
        Response response = client.newCall(request).execute();
        response.close();

        return response;
    }

}
