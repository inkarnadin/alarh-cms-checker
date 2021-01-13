package web.env.webserver;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import web.analyzer.check.ExtendEnvironmentAnalyzer;
import web.env.AbstractEnvironmentProcessor;
import web.env.EnvType;
import web.http.Request;
import web.struct.Destination;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WebServerProcessor extends AbstractEnvironmentProcessor {

    private final Request request;
    private final Destination destination;

    @Override
    @SneakyThrows
    public void process() {
        ExtendEnvironmentAnalyzer extendEnvironmentAnalyzer = new ExtendEnvironmentAnalyzer(request, destination).prepare(protocol, server, EnvType.WEB_SERVER);
        extendEnvironmentAnalyzer.checkViaHeaders("server");
        System.out.println(destination.fetch().get(0));
    }

}