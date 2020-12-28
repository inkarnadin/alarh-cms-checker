package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.CMSType;
import web.cms.moguta.MogutaConnector;
import web.cms.moguta.annotation.MogutaVersion;
import web.struct.Connector;
import web.struct.Processor;

public class MogutaProvider implements Provider<Connector> {

    @Inject
    @MogutaVersion
    private Processor<CMSType> versionProcessor;

    @Override
    public Connector get() {
        return new MogutaConnector(versionProcessor);
    }

}
