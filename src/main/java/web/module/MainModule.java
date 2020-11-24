package web.module;

import com.google.inject.AbstractModule;
import lombok.SneakyThrows;
import web.cms.CMSChecker;
import web.cms.CMSDeterminant;
import web.cms.CMSTypeDestination;
import web.cms.datalife.DataLifeCheckProcessor;
import web.cms.datalife.annotation.DataLifeCheck;
import web.cms.datalife.parser.DataLifeCheckMainPageParser;
import web.cms.joomla.JoomlaCheckProcessor;
import web.cms.joomla.annotation.JoomlaCheck;
import web.cms.joomla.parser.JoomlaCheckMainPageParser;
import web.cms.wordpress.WordPressCheckProcessor;
import web.cms.wordpress.annotation.WordPressCheck;
import web.cms.yii.YiiCheckProcessor;
import web.cms.yii.annotation.YiiCheck;
import web.cms.yii.parser.YiiCheckMainPageParser;
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

        bind(Parser.class).annotatedWith(JoomlaCheck.class).to(JoomlaCheckMainPageParser.class);
        bind(Destination.class).annotatedWith(JoomlaCheck.class).to(CMSTypeDestination.class);
        bind(Processor.class).annotatedWith(JoomlaCheck.class).to(JoomlaCheckProcessor.class);

        bind(Destination.class).annotatedWith(WordPressCheck.class).to(CMSTypeDestination.class);
        bind(Processor.class).annotatedWith(WordPressCheck.class).to(WordPressCheckProcessor.class);

        bind(Parser.class).annotatedWith(YiiCheck.class).to(YiiCheckMainPageParser.class);
        bind(Destination.class).annotatedWith(YiiCheck.class).to(CMSTypeDestination.class);
        bind(Processor.class).annotatedWith(YiiCheck.class).to(YiiCheckProcessor.class);

        bind(Parser.class).annotatedWith(DataLifeCheck.class).to(DataLifeCheckMainPageParser.class);
        bind(Destination.class).annotatedWith(DataLifeCheck.class).to(CMSTypeDestination.class);
        bind(Processor.class).annotatedWith(DataLifeCheck.class).to(DataLifeCheckProcessor.class);

        bind(Determinant.class).annotatedWith(Cms.class).to(CMSDeterminant.class);
        bind(Checker.class).annotatedWith(Cms.class).to(CMSChecker.class);

        bind(Checker.class).annotatedWith(DBAdmin.class).to(DBAdminChecker.class);

        bind(Runner.class);
    }

}
