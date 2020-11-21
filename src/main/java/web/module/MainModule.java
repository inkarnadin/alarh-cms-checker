package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.*;
import web.cms.CMSChecker;
import web.db.DBAdminChecker;
import web.module.annotation.CMS;
import web.module.annotation.DBAdmin;

public class MainModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Checker.class).annotatedWith(CMS.class).to(CMSChecker.class);
        bind(Checker.class).annotatedWith(DBAdmin.class).to(DBAdminChecker.class);

        bind(Runner.class);
    }

}
