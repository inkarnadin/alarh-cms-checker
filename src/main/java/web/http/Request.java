package web.http;

import okhttp3.Response;

public interface Request {

    Response send(Host host);
    Boolean isRedirect(Response response);

}
