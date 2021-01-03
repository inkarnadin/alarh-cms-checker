package web.http;

import okhttp3.Response;

import java.util.Objects;
import java.util.regex.Pattern;

public class HttpValidator {

    public static Boolean isRedirect(Response response) {
        if (Objects.nonNull(response.priorResponse())) {
            Response priorResponse = response.priorResponse();
            return Objects.nonNull(priorResponse) && priorResponse.code() == 301;
        }
        return false;
    }

    public static Boolean isOriginalSource(Response response, String originalHost) {
        Response prior = response.priorResponse();
        if (Objects.nonNull(prior) && prior.code() / 100 == 3) {
            String location = String.join("", prior.headers().values("Location"));
            return Pattern.compile(originalHost).matcher(location).find();
        }
        return true;
    }

}
