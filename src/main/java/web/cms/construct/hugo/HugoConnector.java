package web.cms.construct.hugo;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.ResultContainer;
import web.struct.Processor;

import java.util.Optional;

import static web.cms.CMSMarker.*;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class HugoConnector extends AbstractCMSConnector {

    @Named(HUGO_CHECK)
    private final Processor<CMSType> checkProcessor;
    @Named(HUGO_VERSION)
    private final Processor<CMSType> versionProcessor;

    @Override
    public Pair<CMSType, Optional<ResultContainer>> check() {
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

