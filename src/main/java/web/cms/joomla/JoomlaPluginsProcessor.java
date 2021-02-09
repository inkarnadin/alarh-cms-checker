package web.cms.joomla;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.cms.AbstractCMSProcessor;

import web.http.Request;
import web.http.ResponseBodyHandler;
import web.parser.XMLParser;
import web.printer.Printer;
import web.struct.Destination;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.XML_FILES;
import static web.http.Headers.CONTENT_TYPE;
import static web.printer.PrinterMarker.LIST_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class JoomlaPluginsProcessor extends AbstractCMSProcessor {

    private final Request request;
    private final Destination destination;
    private final XMLParser<String> parser;
    @Named(LIST_PRINTER)
    private final Printer printer;

    private final HashSet<String> components = new HashSet<>();
    private final HashSet<String> modules = new HashSet<>();

    @Override
    public void process() {
        String responseBody = "";
        try (Response response = request.send(host)) {
            responseBody = ResponseBodyHandler.readBody(response);
        }

        Matcher comMatcher = Pattern.compile("[/|\"](com_.*?)[/|\"]").matcher(responseBody);
        while (comMatcher.find())
            components.add(comMatcher.group(1));

        int counter = 0;
        if (components.size() > 0) {
            destination.insert(counter++, String.format("  ** Components (%s):", components.size()));

            for (String component : components) {
                host.setPath(String.format("administrator/components/%s/%s.xml", component, component.replace("com_", "")));
                try (Response response = request.send(host)) {
                    String componentBody = ResponseBodyHandler.readBody(response);
                    String contentType = response.header(CONTENT_TYPE);

                    String version = (Arrays.asList(XML_FILES).contains(contentType))
                            ? parser.parse(componentBody)
                            : "<unknown>";
                    destination.insert(counter++, String.format("   * %s: %s", component, version));
                }
            }
        } else {
            destination.insert(counter++, "  ** Components (0): <unknown>");
        }

        Matcher modMatcher = Pattern.compile("[/|\"](mod_.*?)[/|\"]").matcher(responseBody);
        while (modMatcher.find())
            modules.add(modMatcher.group(1));

        if (modules.size() > 0) {
            destination.insert(counter++, String.format("  ** Modules (%s):", modules.size()));

            for (String module : modules) {
                destination.insert(counter++, String.format("   * %s", module));
            }
        } else {
            destination.insert(counter, "  ** Modules (0): <unknown>");
        }

        printer.print(destination);
    }

}
