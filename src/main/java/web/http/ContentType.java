package web.http;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentType {

    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_XML = "text/xml";

    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_JSON = "application/json";

    public static final String APPLICATION_JAVASCRIPT = "application/javascript";
    public static final String APPLICATION_X_JAVASCRIPT = "application/x-javascript";
    public static final String TEXT_JAVASCRIPT = "text/javascript";

    public static final String IMAGE_JPG = "image/jpeg";
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_SVG = "image/svg";
    public static final String IMAGE_SVG_XML = "image/svg+xml";

    public static String defineContentType(String contentType) {
        if (Objects.isNull(contentType))
            return TEXT_PLAIN;

        Pattern pattern = Pattern.compile("[\\w/-]*");
        Matcher matcher = pattern.matcher(contentType);
        if (matcher.find())
            return matcher.group(0);
        return contentType;
    }

}
