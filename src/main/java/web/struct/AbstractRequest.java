package web.struct;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import web.http.Host;

public abstract class AbstractRequest implements Request {

    protected final static String USER_AGENT_HEADER = "User-Agent";
    protected final static String USER_AGENT_HEADER_VALUE = "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0";

    @Override
    public Response send(Host host) {
        return null;
    }

    protected Response error(String msg) {
        return new Response.Builder()
                .request(new okhttp3.Request.Builder()
                        .url("http://wrong.url.example.com")
                        .build())
                .protocol(Protocol.HTTP_2)
                .code(400)
                .body(ResponseBody.create(msg, MediaType.parse("text/plain")))
                .message(msg)
                .build();
    }

}
