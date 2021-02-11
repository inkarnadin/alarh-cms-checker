package web.validator;

import okhttp3.Response;
import web.http.ResponseBodyHandler;

import java.util.regex.Pattern;

public class ContentValidator implements ResponseValidator {

    public static boolean isGenerated(Response response) {
        String responseBody = ResponseBodyHandler.readBody(response);
        return Pattern.compile("This page generated").matcher(responseBody).find();
    }

}
