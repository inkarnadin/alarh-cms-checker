package web.struct;

import okhttp3.Response;
import web.http.Host;

public interface Request {

    Response send(Host host);

}
