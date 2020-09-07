package joomla.request;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckComponentRequest implements IRequest {

    @SneakyThrows
    public Response send(String... params) {
        // www.youmagic.com
        // com_newsfeeds

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(params[0] + "://" + params[1] + "/index.php?option=" + params[2])
                .method("GET", null)
                //.addHeader("Cookie", "youmagic_com_tpl=youmagic_com")
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

}
