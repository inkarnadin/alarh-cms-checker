package web.cms.classic.umi;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.ResultContainer;
import web.struct.Processor;

import java.util.Optional;

import static web.cms.CMSMarker.UMI_CHECK;
import static web.cms.CMSMarker.UMI_VERSION;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class UmiConnector extends AbstractCMSConnector {

    @Named(UMI_CHECK)
    private final Processor<CMSType> checkProcessor;
    @Named(UMI_VERSION)
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

