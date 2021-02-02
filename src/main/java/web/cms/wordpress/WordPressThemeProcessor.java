package web.cms.wordpress;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.analyzer.theme.Extractor;
import web.cms.AbstractCMSProcessor;
import web.http.Request;
import web.http.ResponseBodyHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static web.cms.CMSMarker.WORDPRESS_THEME;

@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class WordPressThemeProcessor extends AbstractCMSProcessor {

    private final Request request;
    @Named(WORDPRESS_THEME)
    private final Extractor extractor;

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
                    System.out.println(extractor.extract(themeResponseBody));
                }
            }
        }
    }
}
