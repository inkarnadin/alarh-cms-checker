package web.env.whois;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.CMSType;
import web.env.AbstractEnvConnector;
import web.env.EnvType;
import web.struct.Destination;
import web.struct.Processor;

import java.util.Optional;

import static web.env.EnvMarker.WHOIS;

@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class WhoisConnector extends AbstractEnvConnector {

    @Named(WHOIS)
    private final Processor<EnvType> processor;

    @Override
    public Pair<CMSType, Optional<Destination>> check() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
        return null;
    }

}
