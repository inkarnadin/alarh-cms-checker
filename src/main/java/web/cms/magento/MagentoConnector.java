package web.cms.magento;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.Destination;
import web.struct.Processor;

import java.util.Optional;

import static web.cms.CMSMarker.MAGENTO_CHECK;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class MagentoConnector extends AbstractCMSConnector {

    @Named(MAGENTO_CHECK)
    private final Processor<CMSType> checkProcessor;

    @Override
    public Pair<CMSType, Optional<Destination>> check() {
        checkProcessor.configure(params.getProtocol(), params.getServer());
        checkProcessor.process();
        return checkProcessor.transmit();
    }

}
