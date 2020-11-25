package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.drupal.annotation.Drupal;
import web.module.provider.DrupalProvider;
import web.struct.Connector;

public class DrupalModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Connector.class).annotatedWith(Drupal.class).toProvider(DrupalProvider.class);
    }

}
