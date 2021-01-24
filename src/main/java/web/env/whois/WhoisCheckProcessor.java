package web.env.whois;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.analyzer.check.WhoIsAnalyzer;
import web.env.AbstractEnvironmentProcessor;
import web.http.Request;
import web.printer.Printer;
import web.struct.Destination;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WhoisCheckProcessor extends AbstractEnvironmentProcessor {

    private final Request request;
    private final Destination destination;
    @Named("listPrinter")
    private final Printer printer;

    @Override
    public void process() {
        WhoIsAnalyzer whoIsEnvironmentAnalyzer = new WhoIsAnalyzer(request, destination, host);
        whoIsEnvironmentAnalyzer.checkWhoIs();

        printer.print(destination);
    }

}
