package web.cms.wordpress;

import lombok.RequiredArgsConstructor;
import web.Processor;
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
        processor.configure(params.getProtocol(), params.getHost());
        processor.process();
    }

}
