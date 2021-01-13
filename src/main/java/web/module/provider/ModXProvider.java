package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.CMSType;
import web.cms.modx.ModXConnector;
import web.cms.modx.annotation.ModXVersion;
import web.struct.Connector;
import web.struct.Processor;

public class ModXProvider implements Provider<Connector> {

    @Inject
    @ModXVersion
    private Processor<CMSType> versionProcessor;

    @Override
    public Connector get() {
        return new ModXConnector(versionProcessor);
    }

}
