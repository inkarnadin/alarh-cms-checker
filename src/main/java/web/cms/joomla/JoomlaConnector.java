package web.cms.joomla;

import lombok.RequiredArgsConstructor;
import web.struct.Processor;
import web.cms.AbstractCMSConnector;

@RequiredArgsConstructor
public class JoomlaConnector extends AbstractCMSConnector {

    private final Processor processor;

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public void checkPlugins() {
        processor.configure(params.getProtocol(), params.getHost());
        processor.process();
    }

}
