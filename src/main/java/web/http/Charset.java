package web.http;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Charset {

    public static final String UTF8 = "UTF-8";
    public static final String WIN1251 = "windows-1251";
    public static final String ISO_8859_1 = "iso-8859-1";

    public static String defineCharset(String contentType) {
        if (Objects.nonNull(contentType)) {
            Pattern pattern = Pattern.compile(".*charset=(.*)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(contentType);
            if (matcher.find()) {
                if (Objects.equals(matcher.group(1), WIN1251))
                    return WIN1251;
                if (Objects.equals(matcher.group(1), ISO_8859_1))
                    return ISO_8859_1;
            }
        }
        return UTF8;
    }

}
