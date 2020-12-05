package web.cms.bitrix;

import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.cms.CMSType;
import web.struct.Processor;

@RequiredArgsConstructor
public class BitrixConnector extends AbstractCMSConnector {

    private final Processor<CMSType> versionProcessor;

    @Override
    public void checkVersion() {
        versionProcessor.configure(params.getProtocol(), params.getServer());
        versionProcessor.process();
        versionProcessor.transmit().getSecond().ifPresent(x -> x.fetch().forEach(System.out::println));
    }

}
