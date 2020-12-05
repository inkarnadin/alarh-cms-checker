package web.module.provider;

import com.google.inject.Provider;
import web.cms.modx.ModXConnector;
import web.struct.Connector;

public class ModXProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new ModXConnector();
    }

}
