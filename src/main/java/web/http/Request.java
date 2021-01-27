package web.http;

import okhttp3.Response;

import java.util.Map;

public interface Request {

    Response send(Host host);
    void richHeaders(Map<String, String> headers);

}
