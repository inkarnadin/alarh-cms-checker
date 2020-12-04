package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.bitrix.BitrixConnector;
import web.cms.bitrix.annotation.BitrixVersion;
import web.struct.Connector;
import web.struct.Processor;

public class BitrixProvider implements Provider<Connector> {

    private final Processor versionProcessor;

    @Inject
    BitrixProvider(@BitrixVersion Processor versionProcessor) {
        this.versionProcessor = versionProcessor;
    }

    @Override
    public Connector get() {
        return new BitrixConnector(versionProcessor);
    }

}
