package web.env.phpmyadmin;

import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.CMSType;
import web.env.EnvType;
import web.struct.Destination;
import web.struct.Processor;
import web.env.AbstractEnvConnector;

import java.util.Optional;

@RequiredArgsConstructor
public class PhpMyAdminConnector extends AbstractEnvConnector {

    private final Processor<EnvType> processor;

    @Override
    public Pair<CMSType, Optional<Destination>> check() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
        return null;
    }

}
