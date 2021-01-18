package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import lombok.SneakyThrows;
import web.cms.CMSChecker;
import web.env.EnvironmentChecker;
import web.http.Client;
import web.http.GetRequest;
import web.http.HttpClient;
import web.http.Request;
import web.module.annotation.Cms;
import web.module.annotation.Env;
import web.struct.*;

public class MainModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        install(new CommonModule());

        bind(Checker.class).annotatedWith(Cms.class).to(CMSChecker.class);
        bind(Checker.class).annotatedWith(Env.class).to(EnvironmentChecker.class);

        bind(Runner.class);
    }

}
