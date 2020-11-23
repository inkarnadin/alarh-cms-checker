package web.struct;

import okhttp3.Response;

public interface Request {

    Response send(String... params);

}
