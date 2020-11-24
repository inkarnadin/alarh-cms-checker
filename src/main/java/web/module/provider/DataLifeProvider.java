package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.datalife.DataLifeConnector;
import web.struct.Connector;

public class DataLifeProvider implements Provider<Connector> {

    @Inject
    DataLifeProvider() {}

    @Override
    public Connector get() {
        return new DataLifeConnector();
    }

}
