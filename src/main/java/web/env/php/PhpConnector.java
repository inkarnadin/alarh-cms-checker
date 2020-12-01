package web.env.php;

import lombok.RequiredArgsConstructor;
import web.env.AbstractEnvConnector;
import web.struct.Processor;

@RequiredArgsConstructor
public class PhpConnector extends AbstractEnvConnector {

    private final Processor processor;

    @Override
    public void checkVersion() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
    }

}
