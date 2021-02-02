package web.env.whois;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.analyzer.check.WhoIsAnalyzer;
import web.env.AbstractEnvironmentProcessor;
import web.http.Request;
import web.printer.Printer;
import web.struct.Destination;

import static web.printer.PrinterMarker.LIST_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WhoisCheckProcessor extends AbstractEnvironmentProcessor {

    private final Request request;
    private final Destination destination;
    @Named(LIST_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        WhoIsAnalyzer whoIsEnvironmentAnalyzer = new WhoIsAnalyzer(request, destination, host);
        whoIsEnvironmentAnalyzer.checkWhoIs();

        printer.print(destination);
    }

}
