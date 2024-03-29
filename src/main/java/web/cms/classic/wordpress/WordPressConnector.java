package web.cms.classic.wordpress;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.ResultContainer;
import web.struct.Processor;

import java.util.Optional;

import static web.cms.CMSMarker.*;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class WordPressConnector extends AbstractCMSConnector {

    @Named(WORDPRESS_CHECK)
    private final Processor<CMSType> checkProcessor;
    @Named(WORDPRESS_VERSION)
    private final Processor<CMSType> versionProcessor;
    @Named(WORDPRESS_THEME)
    private final Processor<CMSType> themeProcessor;
    @Named(WORDPRESS_PLUGIN)
    private final Processor<CMSType> pluginProcessor;

    @Override
    public Pair<CMSType, Optional<ResultContainer>> check() {
        checkProcessor.configure(params.getProtocol(), params.getServer());
        checkProcessor.process();
        return checkProcessor.transmit();
    }

    @Override
    public void checkVersion() {
        versionProcessor.configure(params.getProtocol(), params.getServer());
        versionProcessor.process();
    }

    @Override
    public void checkTheme() {
        themeProcessor.configure(params.getProtocol(), params.getServer());
        themeProcessor.process();
    }

    @Override
    public void checkPlugins() {
        pluginProcessor.configure(params.getProtocol(), params.getServer());
        pluginProcessor.process();
    }

}
