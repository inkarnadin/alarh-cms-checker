package web.cms.modx;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.Destination;
import web.struct.Processor;

import java.util.Optional;

import static web.cms.CMSMarker.MODX_CHECK;
import static web.cms.CMSMarker.MODX_VERSION;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class ModXConnector extends AbstractCMSConnector {

    @Named(MODX_CHECK)
    private final Processor<CMSType> checkProcessor;
    @Named(MODX_VERSION)
    private final Processor<CMSType> versionProcessor;

    @Override
    public Pair<CMSType, Optional<Destination>> check() {
        checkProcessor.configure(params.getProtocol(), params.getServer());
        checkProcessor.process();
        return checkProcessor.transmit();
    }
    @Override
    public void checkVersion() {
        versionProcessor.configure(params.getProtocol(), params.getServer());
        versionProcessor.process();
    }

}
