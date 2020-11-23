package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.CMSChecker;
import web.cms.CMSDeterminant;
import web.cms.CMSTypeDestination;
import web.cms.joomla.JoomlaCheckProcessor;
import web.cms.joomla.annotation.JoomlaCheck;
import web.cms.wordpress.WordPressCheckProcessor;
import web.cms.wordpress.annotation.WordPressCheck;
import web.db.DBAdminChecker;
import web.http.GetRequest;
import web.http.Request;
import web.module.annotation.Cms;
import web.module.annotation.DBAdmin;
import web.module.annotation.Get;
import web.struct.*;

public class MainModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(Get.class).to(GetRequest.class);
        bind(Destination.class).annotatedWith(JoomlaCheck.class).to(CMSTypeDestination.class);
        bind(Processor.class).annotatedWith(JoomlaCheck.class).to(JoomlaCheckProcessor.class);

        bind(Request.class).annotatedWith(Get.class).to(GetRequest.class);
        bind(Destination.class).annotatedWith(WordPressCheck.class).to(CMSTypeDestination.class);
        bind(Processor.class).annotatedWith(WordPressCheck.class).to(WordPressCheckProcessor.class);

        bind(Determinant.class).annotatedWith(Cms.class).to(CMSDeterminant.class);
        bind(Checker.class).annotatedWith(Cms.class).to(CMSChecker.class);

        bind(Checker.class).annotatedWith(DBAdmin.class).to(DBAdminChecker.class);

        bind(Runner.class);
    }

}
