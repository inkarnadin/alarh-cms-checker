package web.cms.framework.django;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.Processor;
import web.struct.ResultContainer;

import java.util.Optional;

import static web.cms.CMSMarker.DJANGO_CHECK;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class DjangoConnector extends AbstractCMSConnector {

    @Named(DJANGO_CHECK)
    private final Processor<CMSType> checkProcessor;

    @Override
    public Pair<CMSType, Optional<ResultContainer>> check() {
        checkProcessor.configure(params.getProtocol(), params.getServer());
        checkProcessor.process();
        return checkProcessor.transmit();
    }

}
