package web.cms.wordpress;

import lombok.RequiredArgsConstructor;
import web.AbstractConnector;
import web.PluginProcessor;

@RequiredArgsConstructor
public class WordPressConnector extends AbstractConnector {

    private final PluginProcessor pluginProcessor;

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public void checkPlugins() {
        pluginProcessor.configure(protocol, host);
        pluginProcessor.process();
    }

}
