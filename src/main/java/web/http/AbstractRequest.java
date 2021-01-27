package web.http;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static web.http.ContentType.TEXT_PLAIN;

public abstract class AbstractRequest implements Request {

    protected final static String USER_AGENT_HEADER_VALUE = "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0";
    protected final static String BEGET_PROTECTION_COOKIE = "beget=begetok;";

    protected Map<String, String> headers = new HashMap<>();

    @Override
    public Response send(Host host) {
        return null;
    }

    @Override
    public void richHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    protected Response error(String msg, Host host) {
        return new Response.Builder()
                .request(new okhttp3.Request.Builder()
                        .url(host.createUrl())
                        .build())
                .protocol(Protocol.HTTP_2)
                .code(400)
                .body(ResponseBody.create(Objects.nonNull(msg) ? msg : "", MediaType.parse(TEXT_PLAIN)))
                .message(msg)
                .build();
    }

}
