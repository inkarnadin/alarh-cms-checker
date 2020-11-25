package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import lombok.SneakyThrows;
import web.cms.CMSChecker;
import web.cms.CMSDeterminant;
import web.cms.CMSType;
import web.cms.datalife.DataLifeCheckProcessor;
import web.cms.datalife.annotation.DataLife;
import web.cms.joomla.JoomlaCheckProcessor;
import web.cms.joomla.annotation.Joomla;
import web.cms.wordpress.WordPressCheckProcessor;
import web.cms.wordpress.annotation.WordPress;
import web.cms.yii.YiiCheckProcessor;
import web.cms.yii.annotation.Yii;
import web.db.DBAdminChecker;
import web.http.GetRequest;
import web.http.Request;
import web.module.annotation.Cms;
import web.module.annotation.DBAdmin;
import web.module.annotation.Get;
import web.parser.BooleanReturnTextParser;
import web.parser.TextParser;
import web.struct.*;

public class MainModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Request.class).annotatedWith(Get.class).to(GetRequest.class);

        bind(new TypeLiteral<TextParser<Boolean>>(){}).to(BooleanReturnTextParser.class);
        bind(Destination.class).to(SimpleDestination.class).in(Scopes.NO_SCOPE);

        bind(Processor.class).annotatedWith(Joomla.class).to(JoomlaCheckProcessor.class);
        bind(Processor.class).annotatedWith(WordPress.class).to(WordPressCheckProcessor.class);
        bind(Processor.class).annotatedWith(Yii.class).to(YiiCheckProcessor.class);
        bind(Processor.class).annotatedWith(DataLife.class).to(DataLifeCheckProcessor.class);

        bind(new TypeLiteral<Determinant<CMSType, Destination>>(){}).to(CMSDeterminant.class);

        bind(Checker.class).annotatedWith(Cms.class).to(CMSChecker.class);

        bind(Checker.class).annotatedWith(DBAdmin.class).to(DBAdminChecker.class);

        bind(Runner.class);
    }

}
