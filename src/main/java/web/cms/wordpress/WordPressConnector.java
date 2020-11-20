package web.cms.wordpress;

import lombok.RequiredArgsConstructor;
import web.Processor;
import web.cms.AbstractConnector;

@RequiredArgsConstructor
public class WordPressConnector extends AbstractConnector {

    private final Processor processor;

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public void checkPlugins() {
        processor.configure(protocol, host);
        processor.process();
    }

}
