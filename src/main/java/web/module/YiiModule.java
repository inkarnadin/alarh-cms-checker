package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.yii.annotation.Yii;
import web.module.provider.YiiProvider;
import web.struct.Connector;

public class YiiModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Connector.class).annotatedWith(Yii.class).toProvider(YiiProvider.class);
    }

}
