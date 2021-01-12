package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.env.EnvType;
import web.env.webserver.WebServerConnector;
import web.env.webserver.annotation.WebServer;
import web.struct.Connector;
import web.struct.Processor;

public class WebServerProvider implements Provider<Connector> {

    @Inject @WebServer
    private Processor<EnvType> processor;

    @Override
    public Connector get() {
        return new WebServerConnector(processor);
    }

}
