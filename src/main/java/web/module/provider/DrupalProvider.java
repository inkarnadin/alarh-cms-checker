package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.drupal.DrupalConnector;
import web.struct.Connector;

public class DrupalProvider implements Provider<Connector> {

    @Inject
    DrupalProvider() {}

    @Override
    public Connector get() {
        return new DrupalConnector();
    }

}
