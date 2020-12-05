package web.module.provider;

import com.google.inject.Provider;
import web.cms.insales.InSalesConnector;
import web.struct.Connector;

public class InSalesProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new InSalesConnector();
    }

}
