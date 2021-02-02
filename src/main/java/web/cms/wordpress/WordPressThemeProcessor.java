package web.cms.wordpress;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.cms.AbstractCMSProcessor;
import web.http.Request;
import web.http.ResponseBodyHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class WordPressThemeProcessor extends AbstractCMSProcessor {

    private final Request request;

    @Override
    public void process() {
        try (Response response = request.send(host)) {
            String responseBody = ResponseBodyHandler.readBody(response);
            Pattern pattern = Pattern.compile("wp-content/themes/(.*?)/style.css");
            Matcher matcher = pattern.matcher(responseBody);

            if (matcher.find()) {
                host.setPath(matcher.group(0));
                try (Response themeResponse = request.send(host)) {
                    String themeResponseBody = ResponseBodyHandler.readBody(themeResponse);

                    //System.out.println(matcher.group(1));

                    Pattern themeNamePattern = Pattern.compile("Theme Name:\\s*(.*)");
                    Matcher themeNameMatcher = themeNamePattern.matcher(themeResponseBody);

                    if (themeNameMatcher.find())
                        System.out.println(themeNameMatcher.group(1));

                    Pattern themeDescriptionPattern = Pattern.compile("Description:\\s*(.*)");
                    Matcher themeDescriptionMatcher = themeDescriptionPattern.matcher(themeResponseBody);

                    if (themeDescriptionMatcher.find())
                        System.out.println(themeDescriptionMatcher.group(1));

                    Pattern themeVersionPattern = Pattern.compile("Version:\\s*(.*)");
                    Matcher themeVersionMatcher = themeVersionPattern.matcher(themeResponseBody);

                    if (themeVersionMatcher.find())
                        System.out.println(themeVersionMatcher.group(1));

                    Pattern themeAuthorPattern = Pattern.compile("Author:\\s*(.*)");
                    Matcher themeAuthorMatcher = themeAuthorPattern.matcher(themeResponseBody);

                    if (themeAuthorMatcher.find())
                        System.out.println(themeAuthorMatcher.group(1));

                }
            }
        }
    }
}
