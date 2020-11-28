package web.http;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.Objects;

import static web.http.ContentType.TEXT_PLAIN;

public abstract class AbstractRequest implements Request {

    protected final static String USER_AGENT_HEADER_VALUE = "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0";
    protected final static String BEGET_PROTECTION_COOKIE = "beget=begetok;";

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
                .body(ResponseBody.create(Objects.nonNull(msg) ? msg : "", MediaType.parse(TEXT_PLAIN)))
                .message(msg)
                .build();
    }

}
