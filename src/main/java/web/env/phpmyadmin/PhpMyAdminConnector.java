package web.env.phpmyadmin;

import lombok.RequiredArgsConstructor;
import web.env.EnvType;
import web.struct.Processor;
import web.env.AbstractEnvConnector;

@RequiredArgsConstructor
public class PhpMyAdminConnector extends AbstractEnvConnector {

    private final Processor<EnvType> processor;

    @Override
    public boolean check() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
        return true;
    }

}
