package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.datalife.DataLifeConnector;
import web.cms.datalife.annotation.DataLifeVersion;
import web.struct.Connector;
import web.struct.Processor;

public class DataLifeProvider implements Provider<Connector> {

    private final Processor versionProcessor;

    @Inject
    DataLifeProvider(@DataLifeVersion Processor versionProcessor) {
        this.versionProcessor = versionProcessor;
    }

    @Override
    public Connector get() {
        return new DataLifeConnector(versionProcessor);
    }

}
