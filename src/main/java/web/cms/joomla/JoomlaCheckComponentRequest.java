package web.cms.joomla;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import web.cms.AbstractRequest;

public class JoomlaCheckComponentRequest extends AbstractRequest {

    @SneakyThrows
    public Response send(String... params) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(params[0] + "://" + params[1] + "/administrator/components/" + params[2])
                .method("GET", null)
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0")
                .build();
        Response response = client.newCall(request).execute();
        response.close();

        return response;
    }

}
