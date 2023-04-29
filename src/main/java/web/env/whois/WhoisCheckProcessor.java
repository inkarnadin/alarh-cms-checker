package web.env.whois;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.analyzer.check.WhoIsAnalyzer;
import web.env.AbstractEnvironmentProcessor;
import web.http.Request;
import web.printer.Printer;
import web.struct.ResultContainer;

import static web.printer.PrinterMarker.LIST_PRINTER;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WhoisCheckProcessor extends AbstractEnvironmentProcessor {

    private final Request request;
    private final ResultContainer resultContainer;
    @Named(LIST_PRINTER)
    private final Printer printer;

    @Override
    public void process() {
        WhoIsAnalyzer whoIsEnvironmentAnalyzer = new WhoIsAnalyzer(request, resultContainer, host);
        whoIsEnvironmentAnalyzer.checkWhoIs();

        printer.print(resultContainer);
    }

}
