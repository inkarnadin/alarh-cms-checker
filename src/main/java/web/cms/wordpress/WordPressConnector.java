package web.cms.wordpress;

import lombok.RequiredArgsConstructor;
import web.struct.Processor;
import web.cms.AbstractCMSConnector;

@RequiredArgsConstructor
public class WordPressConnector extends AbstractCMSConnector {

    private final Processor processor;

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public void checkPlugins() {
        processor.configure(params.getProtocol(), params.getServer());
        processor.process();
    }

}
