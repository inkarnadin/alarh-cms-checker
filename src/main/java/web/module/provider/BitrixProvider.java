package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.bitrix.BitrixConnector;
import web.struct.Connector;

public class BitrixProvider implements Provider<Connector> {

    @Inject
    BitrixProvider() {}

    @Override
    public Connector get() {
        return new BitrixConnector();
    }

}
