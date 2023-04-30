package web.cms.framework.expressjs;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.Processor;
import web.struct.ResultContainer;

import java.util.Optional;

import static web.cms.CMSMarker.EXPRESS_JS_CHECK;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class ExpressJsConnector extends AbstractCMSConnector {

    @Named(EXPRESS_JS_CHECK)
    private final Processor<CMSType> checkProcessor;

    @Override
    public Pair<CMSType, Optional<ResultContainer>> check() {
        checkProcessor.configure(params.getProtocol(), params.getServer());
        checkProcessor.process();
        return checkProcessor.transmit();
    }

}
