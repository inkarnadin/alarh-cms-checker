package web.env.php;

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

import static web.env.EnvMarker.PHP;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class PhpConnector extends AbstractEnvConnector {

    @Named(PHP)
    private final Processor<EnvType> processor;

    @Override
    public Pair<CMSType, Optional<ResultContainer>> check() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
        return null;
    }

}
