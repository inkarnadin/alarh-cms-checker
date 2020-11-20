package web;

import okhttp3.Response;

public abstract class AbstractRequest implements Request {

    protected final static String GET = "GET";

    protected final static String USER_AGENT_HEADER = "User-Agent";
    protected final static String USER_AGENT_HEADER_VALUE = "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0";

    @Override
    public Response send(String... params) {
        return null;
    }

}
