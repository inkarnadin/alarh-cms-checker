package web.env.webserver;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.CMSType;
import web.env.AbstractEnvConnector;
import web.env.EnvType;
import web.struct.ResultContainer;
import web.struct.Processor;

import java.util.Optional;

import static web.env.EnvMarker.WEB_SERVER;

@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class WebServerConnector extends AbstractEnvConnector {

    @Named(WEB_SERVER)
    private final Processor<EnvType> processor;

    @Override
    public Pair<CMSType, Optional<ResultContainer>> check() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
        return null;
    }

}
