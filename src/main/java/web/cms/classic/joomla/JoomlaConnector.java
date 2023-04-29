package web.cms.classic.joomla;

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
public class JoomlaConnector extends AbstractCMSConnector {

    @Named(JOOMLA_CHECK)
    private final Processor<CMSType> checkProcessor;
    @Named(JOOMLA_VERSION)
    private final Processor<CMSType> versionProcessor;
    @Named(JOOMLA_PLUGIN)
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
    public void checkPlugins() {
        pluginProcessor.configure(params.getProtocol(), params.getServer());
        pluginProcessor.process();
    }

}
