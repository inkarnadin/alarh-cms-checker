package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.CMSType;
import web.cms.umi.UmiConnector;
import web.cms.umi.annotation.UmiVersion;
import web.struct.Connector;
import web.struct.Processor;

public class UmiProvider implements Provider<Connector> {

    @Inject
    @UmiVersion
    private Processor<CMSType> versionProcessor;

    @Override
    public Connector get() {
        return new UmiConnector(versionProcessor);
    }

}
