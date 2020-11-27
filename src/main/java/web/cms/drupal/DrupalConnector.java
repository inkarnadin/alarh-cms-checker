package web.cms.drupal;

import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;
import web.struct.Processor;

@RequiredArgsConstructor
public class DrupalConnector extends AbstractCMSConnector {

    private final Processor versionProcessor;

    @Override
    public void checkVersion() {
        versionProcessor.configure(params.getProtocol(), params.getServer());
        versionProcessor.process();
        versionProcessor.transmit().ifPresent(x -> x.fetch().forEach(System.out::println));
    }

}

