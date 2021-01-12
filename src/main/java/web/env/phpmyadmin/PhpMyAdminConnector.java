package web.env.phpmyadmin;

import lombok.RequiredArgsConstructor;
import web.env.EnvType;
import web.struct.Processor;
import web.env.AbstractEnvConnector;

@RequiredArgsConstructor
public class PhpMyAdminConnector extends AbstractEnvConnector {

    private final Processor<EnvType> processor;

    @Override
    public void checkVersion() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
    }

}
