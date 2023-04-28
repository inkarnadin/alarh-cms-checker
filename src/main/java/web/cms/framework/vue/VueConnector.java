package web.cms.framework.vue;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.Destination;
import web.struct.Processor;

import java.util.Optional;

import static web.cms.CMSMarker.VUE_CHECK;
import static web.cms.CMSMarker.VUE_VERSION;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class VueConnector extends AbstractCMSConnector {

    @Named(VUE_CHECK)
    private final Processor<CMSType> checkProcessor;
    @Named(VUE_VERSION)
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

