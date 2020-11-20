package web.cms.joomla;

import lombok.RequiredArgsConstructor;
import web.AbstractConnector;
import web.PluginProcessor;

@RequiredArgsConstructor
public class JoomlaConnector extends AbstractConnector {

    private final PluginProcessor pluginProcessor;

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public void checkPlugins() {
        pluginProcessor.configure(protocol, url);
        pluginProcessor.process();
    }

}
