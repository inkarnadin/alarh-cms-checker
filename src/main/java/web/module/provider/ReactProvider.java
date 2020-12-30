package web.module.provider;

import com.google.inject.Provider;
import web.cms.react.ReactConnector;
import web.struct.Connector;

public class ReactProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new ReactConnector();
    }

}
