package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.CMSChecker;
import web.cms.CMSDeterminant;
import web.cms.CMSTypeDestination;
import web.cms.joomla.JoomlaCheckProcessor;
import web.cms.joomla.JoomlaCheckRequest;
import web.cms.joomla.annotation.JoomlaCheck;
import web.cms.wordpress.WordPressCheckProcessor;
import web.cms.wordpress.WordPressCheckRequest;
import web.cms.wordpress.annotation.WordPressCheck;
import web.db.DBAdminChecker;
import web.module.annotation.CMS;
import web.module.annotation.DBAdmin;
import web.struct.*;

public class MainModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(JoomlaCheck.class).to(JoomlaCheckRequest.class);
        bind(Destination.class).annotatedWith(JoomlaCheck.class).to(CMSTypeDestination.class);
        bind(Processor.class).annotatedWith(JoomlaCheck.class).to(JoomlaCheckProcessor.class);

        bind(Request.class).annotatedWith(WordPressCheck.class).to(WordPressCheckRequest.class);
        bind(Destination.class).annotatedWith(WordPressCheck.class).to(CMSTypeDestination.class);
        bind(Processor.class).annotatedWith(WordPressCheck.class).to(WordPressCheckProcessor.class);

        bind(Determinant.class).annotatedWith(CMS.class).to(CMSDeterminant.class);
        bind(Checker.class).annotatedWith(CMS.class).to(CMSChecker.class);

        bind(Checker.class).annotatedWith(DBAdmin.class).to(DBAdminChecker.class);

        bind(Runner.class);
    }

}
