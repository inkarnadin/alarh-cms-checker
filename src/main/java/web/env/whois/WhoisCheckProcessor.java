package web.env.whois;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.analyzer.check.ExtendEnvironmentAnalyzer;
import web.env.AbstractEnvironmentProcessor;
import web.env.EnvType;
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
        ExtendEnvironmentAnalyzer extendEnvironmentAnalyzer = new ExtendEnvironmentAnalyzer(request, null, destination).prepare(host, EnvType.WHOIS);
        extendEnvironmentAnalyzer.checkWhoIs();

        printer.print(destination);
    }

}
