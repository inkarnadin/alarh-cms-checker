package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.CMSChecker;
import web.env.EnvironmentChecker;
import web.module.annotation.Cms;
import web.module.annotation.Env;
import web.struct.*;

public class MainModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Checker.class).annotatedWith(Cms.class).to(CMSChecker.class);
        bind(Checker.class).annotatedWith(Env.class).to(EnvironmentChecker.class);

        bind(Runner.class);
    }

}
