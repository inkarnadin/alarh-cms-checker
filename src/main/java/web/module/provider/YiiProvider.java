package web.module.provider;

import com.google.inject.Provider;
import web.cms.yii.YiiConnector;
import web.struct.Connector;

public class YiiProvider implements Provider<Connector> {

    @Override
    public Connector get() {
        return new YiiConnector();
    }

}
