package web.http;

import okhttp3.Response;

import java.util.Objects;

public class HttpValidator {

    public static Boolean isRedirect(Response response) {
        if (Objects.nonNull(response.priorResponse())) {
            Response priorResponse = response.priorResponse();
            return Objects.nonNull(priorResponse) && priorResponse.code() == 301;
        }
        return false;
    }

}
