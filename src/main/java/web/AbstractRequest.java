package web;

import okhttp3.Response;

public class AbstractRequest implements Request {

    private final static String USER_AGENT_HEADER = "User-Agent";
    private final static String USER_AGENT_HEADER_VALUE = "Mozilla/5.0 (X11; Linux x86_64; rv:68.0) Gecko/20100101 Firefox/68.0";

    @Override
    public Response send(String... params) {
        return null;
    }

}
