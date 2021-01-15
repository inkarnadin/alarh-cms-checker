package web.env.php;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.env.AbstractEnvConnector;
import web.env.EnvType;
import web.struct.Processor;

import static web.env.EnvMarker.PHP;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class PhpConnector extends AbstractEnvConnector {

    @Named(PHP)
    private final Processor<EnvType> processor;

    @Override
    public void checkVersion() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
    }

}
