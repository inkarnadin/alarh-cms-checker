package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.CMSType;
import web.cms.datalife.DataLifeConnector;
import web.cms.datalife.annotation.DataLifeVersion;
import web.struct.Connector;
import web.struct.Processor;

public class DataLifeProvider implements Provider<Connector> {

    @Inject @DataLifeVersion
    private Processor<CMSType> versionProcessor;

    @Override
    public Connector get() {
        return new DataLifeConnector(versionProcessor);
    }

}
