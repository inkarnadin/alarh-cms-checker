package web.cms.wordpress;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.cms.AbstractCMSProcessor;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.printer.Printer;
import web.struct.Destination;
import web.struct.Source;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static web.cms.CMSMarker.WORDPRESS_PLUGIN;
import static web.printer.PrinterMarker.PLUGIN_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WordPressPluginProcessor extends AbstractCMSProcessor {

    private final Request request;
    private final Destination destination;
    @Named(WORDPRESS_PLUGIN)
    private final Source source;
    @Named(PLUGIN_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        HashSet<String> plugins = new HashSet<>();

        try (Response response = request.send(host)) {
            String responseBody = ResponseBodyHandler.readBody(response);

            Pattern pattern = Pattern.compile("wp-content\\\\?/plugins\\\\?/(.*?)/");
            Matcher matcher = pattern.matcher(responseBody);

            while (matcher.find())
                plugins.add(matcher.group(1).replace("\\", ""));
        }

        destination.insert(0, "  ** Plugins:");

        int i = 1;
        for (String plugin : plugins)
            destination.insert(i++, plugin);

        printer.print(destination);
    }

}
