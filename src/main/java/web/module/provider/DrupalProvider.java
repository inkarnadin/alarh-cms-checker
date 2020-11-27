package web.module.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import web.cms.drupal.DrupalConnector;
import web.cms.drupal.annotation.DrupalVersion;
import web.struct.Connector;
import web.struct.Processor;

public class DrupalProvider implements Provider<Connector> {

    @Inject @DrupalVersion
    private Processor versionProcessor;

    @Override
    public Connector get() {
        return new DrupalConnector(versionProcessor);
    }

}
