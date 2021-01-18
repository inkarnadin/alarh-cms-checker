package web.http;

import okhttp3.Request;
import okhttp3.Response;

public interface Client {

    Response execute(Request request);
    void evictAll();

}
