package web.env.webserver;

import lombok.RequiredArgsConstructor;
import web.env.AbstractEnvConnector;
import web.env.EnvType;
import web.struct.Processor;

@RequiredArgsConstructor
public class WebServerConnector extends AbstractEnvConnector {

    private final Processor<EnvType> processor;

    @Override
    public boolean check() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
        return true;
    }

}
