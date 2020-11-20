package web.cms.wordpress;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import web.AbstractRequest;

public class WordPressCheckPluginRequest extends AbstractRequest {

    @Override
    @SneakyThrows
    public Response send(String... params) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(params[0] + "://" + params[1] + "/wp-content/plugins/" + params[2])
                .method("GET", null)
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0")
                .build();
        Response response = client.newCall(request).execute();
        response.close();

        return response;
    }

}