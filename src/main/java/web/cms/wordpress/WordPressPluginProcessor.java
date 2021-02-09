package web.cms.wordpress;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.analyzer.plugin.PluginObject;
import web.analyzer.theme.Extractor;
import web.cms.AbstractCMSProcessor;
import web.http.ContentType;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.printer.Printer;
import web.struct.Destination;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static web.cms.CMSMarker.WORDPRESS_PLUGIN;
import static web.http.Headers.CONTENT_TYPE;
import static web.http.RequestMarker.HEAD;
import static web.printer.PrinterMarker.LIST_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WordPressPluginProcessor extends AbstractCMSProcessor {

    private final Destination destination;
    private final Request request;
    @Named(HEAD)
    private final Request headRequest;
    @Named(WORDPRESS_PLUGIN)
    private final Extractor<PluginObject> extractor;
    @Named(LIST_PRINTER)
    private final Printer printer;

    private final HashSet<String> plugins = new HashSet<>();

    @Override
    public void process() {
        try (Response response = request.send(host)) {
            String responseBody = ResponseBodyHandler.readBody(response);

            Pattern pattern = Pattern.compile("wp-content\\\\?/plugins\\\\?/(.*?)[/\"]");
            Matcher matcher = pattern.matcher(responseBody);

            while (matcher.find())
                plugins.add(matcher.group(1).replace("\\", ""));
        }

        for (WordPressPlugin src : WordPressPlugin.values()) {
            if (plugins.contains(src.getPath()))
                continue;

            host.setPath(String.format("wp-content/plugins/%s/readme.txt", src.getPath()));
            try (Response response = headRequest.send(host)) {
                String contentType = ContentType.defineContentType(response.header(CONTENT_TYPE));
                if (contentType.equals(ContentType.TEXT_PLAIN) && response.code() != 404)
                    plugins.add(src.getPath());
            }
        }

        if (plugins.size() > 0) {
            destination.insert(0, String.format("  ** Plugins (%s):", plugins.size()));

            int i = 1;
            for (String plugin : plugins) {
                host.setPath(String.format("wp-content/plugins/%s/readme.txt", plugin));
                try (Response response = request.send(host)) {
                    String contentType = ContentType.defineContentType(response.header(CONTENT_TYPE));
                    String preName = WordPressPlugin.search(plugin);

                    if (contentType.equals(ContentType.TEXT_PLAIN)) {
                        String readmeBody = ResponseBodyHandler.readBody(response);

                        PluginObject pluginObject = extractor.extract(readmeBody);
                        pluginObject.setPath(host.getPath());

                        preName = (preName.length() > 0)
                                    ? preName
                                    : pluginObject.isFilled()
                                        ? pluginObject.getName()
                                        : plugin;
                        pluginObject.setName(preName);
                        destination.insert(i++, String.format("   * %s", pluginObject.toString()));
                    } else {
                        destination.insert(i++, String.format("   * %s: %s", (preName.length() > 0) ? preName : plugin + " (?)", "<unknown>"));
                    }
                }
            }
        } else {
            destination.insert(0, "  ** Plugins (0): <unknown>");
        }

        printer.print(destination);
    }

}
