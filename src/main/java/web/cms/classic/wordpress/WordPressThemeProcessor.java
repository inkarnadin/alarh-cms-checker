package web.cms.classic.wordpress;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.analyzer.theme.Extractor;
import web.analyzer.theme.ThemeObject;
import web.cms.AbstractCMSProcessor;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.printer.Printer;
import web.struct.ResultContainer;
import web.struct.Preferences;
import web.struct.Source;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static web.cms.CMSMarker.WORDPRESS_THEME;
import static web.printer.PrinterMarker.LIST_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class WordPressThemeProcessor extends AbstractCMSProcessor {

    private final Request request;
    private final ResultContainer resultContainer;
    @Named(WORDPRESS_THEME)
    private final Extractor<ThemeObject> extractor;
    @Named(WORDPRESS_THEME)
    private final Source source;
    @Named(LIST_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        try (Response response = request.send(host)) {
            String responseBody = ResponseBodyHandler.readBody(response);
            Pattern pattern = Pattern.compile("wp-content/themes/(.*?)/");
            Matcher matcher = pattern.matcher(responseBody);

            ThemeObject themeObject = new ThemeObject();
            if (matcher.find()) {
                String path = matcher.group(0);
                host.setPath(path + "/style.css");
                try (Response themeResponse = request.send(host)) {
                    String themeResponseBody = ResponseBodyHandler.readBody(themeResponse);
                    themeObject = extractor.extract(themeResponseBody);
                    themeObject.setPath(path);

                    String themeName = (Objects.nonNull(themeObject.getThemeName()))
                        ? themeObject.getThemeName().toLowerCase()
                        : "";
                    boolean isCustom = !source.getSources().contains(themeName);
                    themeObject.setIsCustom(isCustom);

                    if (Preferences.isEnableThemeFullInfo()) {
                        resultContainer.add(0, "  ** Theme:");
                        resultContainer.add(1, themeObject.toString());
                    } else {
                        resultContainer.add(0, String.format("  ** Theme: %s", themeObject.getShortInfo()));
                    }
                }
            } else {
                resultContainer.add(0, String.format("  ** Theme: %s", themeObject.getShortInfo()));
            }

            printer.print(resultContainer);
        }
    }
}
