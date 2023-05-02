package web.cms.classic.bitrix;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.ResultContainer;
import web.struct.Processor;

import java.util.Optional;

import static web.cms.CMSMarker.BITRIX_CHECK;
import static web.cms.CMSMarker.BITRIX_DATA;
import static web.cms.CMSMarker.BITRIX_VERSION;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class BitrixConnector extends AbstractCMSConnector {

    @Named(BITRIX_CHECK)
    private final Processor<CMSType> checkProcessor;
    @Named(BITRIX_VERSION)
    private final Processor<CMSType> versionProcessor;
    @Named(BITRIX_DATA)
    private final Processor<CMSType> dataProcessor;

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
    public void checkData() {
        dataProcessor.configure(params.getProtocol(), params.getServer());
        dataProcessor.process();
    }

}
