package web.cms.framework.codeigniter;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.ResultContainer;
import web.struct.Processor;

import java.util.Optional;

import static web.cms.CMSMarker.CODE_IGNITER_CHECK;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class CodeIgniterConnector extends AbstractCMSConnector {

    @Named(CODE_IGNITER_CHECK)
    private final Processor<CMSType> checkProcessor;

    @Override
    public Pair<CMSType, Optional<ResultContainer>> check() {
        checkProcessor.configure(params.getProtocol(), params.getServer());
        checkProcessor.process();
        return checkProcessor.transmit();
    }

}
