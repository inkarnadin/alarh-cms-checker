package web.cms.datalife;

import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.struct.Processor;

@RequiredArgsConstructor
public class DataLifeConnector extends AbstractCMSConnector {

    private final Processor versionProcessor;

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public void checkVersion() {
        versionProcessor.configure(params.getProtocol(), params.getServer());
        versionProcessor.process();
        versionProcessor.transmit().ifPresent(x -> x.fetch().forEach(System.out::println));
    }

}

