package web.cms.wordpress;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.analyzer.theme.Extractor;
import web.analyzer.theme.ThemeObject;
import web.cms.AbstractCMSProcessor;
import web.env.EnvType;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.printer.Printer;
import web.struct.Destination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static web.cms.CMSMarker.WORDPRESS_THEME;

@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class WordPressThemeProcessor extends AbstractCMSProcessor {

    private final Request request;
    private final Destination destination;
    @Named(WORDPRESS_THEME)
    private final Extractor extractor;
    @Named("listPrinter")
    private final Printer printer;

    @Override
    public void process() {
        try (Response response = request.send(host)) {
            String responseBody = ResponseBodyHandler.readBody(response);
            Pattern pattern = Pattern.compile("wp-content/themes/(.*?)/");
            Matcher matcher = pattern.matcher(responseBody);

            if (matcher.find()) {
                host.setPath(matcher.group(0) + "/style.css");
                try (Response themeResponse = request.send(host)) {
                    String themeResponseBody = ResponseBodyHandler.readBody(themeResponse);
                    ThemeObject themeObject = extractor.extract(themeResponseBody);

                    destination.insert(0, "  ** Theme:");
                    destination.insert(1, themeObject.toString());

                    printer.print(destination);
                }
            }
        }
    }
}
