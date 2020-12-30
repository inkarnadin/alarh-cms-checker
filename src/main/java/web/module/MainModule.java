package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import lombok.SneakyThrows;
import web.cms.CMSChecker;
import web.cms.CMSDeterminant;
import web.cms.CMSType;
import web.cms.bitrix.BitrixCheckProcessor;
import web.cms.datalife.DataLifeCheckProcessor;
import web.cms.drupal.DrupalCheckProcessor;
import web.cms.insales.InSalesCheckProcessor;
import web.cms.joomla.JoomlaCheckProcessor;
import web.cms.lavarel.LavarelCheckProcessor;
import web.cms.magento.MagentoCheckProcessor;
import web.cms.maxsite.MaxSiteCheckProcessor;
import web.cms.modx.ModXCheckProcessor;
import web.cms.moguta.MogutaCheckProcessor;
import web.cms.nuxt.NuxtCheckProcessor;
import web.cms.opencart.OpenCardCheckProcessor;
import web.cms.rails.RubyOnRailsCheckProcessor;
import web.cms.react.ReactCheckProcessor;
import web.cms.tilda.TildaCheckProcessor;
import web.cms.vamshop.VamShopCheckProcessor;
import web.cms.vigbo.VigboCheckProcessor;
import web.cms.vue.VueCheckProcessor;
import web.cms.wordpress.WordPressCheckProcessor;
import web.cms.yii.YiiCheckProcessor;
import web.env.EnvironmentChecker;
import web.http.Client;
import web.http.GetRequest;
import web.http.HttpClient;
import web.http.Request;
import web.module.annotation.Cms;
import web.module.annotation.Env;
import web.parser.BooleanReturnTextParser;
import web.parser.TextParser;
import web.struct.*;

public class MainModule extends AbstractModule {

    @SneakyThrows
    @Override
    protected void configure() {
        bind(Client.class).to(HttpClient.class);
        bind(Request.class).to(GetRequest.class);

        bind(new TypeLiteral<TextParser<Boolean>>(){}).to(BooleanReturnTextParser.class);
        bind(Destination.class).to(SimpleDestination.class).in(Scopes.NO_SCOPE);

        Multibinder<Processor<CMSType>> processorCheckMultibinder = Multibinder.newSetBinder(binder(), new TypeLiteral<>() {});
        processorCheckMultibinder.addBinding().to(JoomlaCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(WordPressCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(YiiCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(DataLifeCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(MaxSiteCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(DrupalCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(BitrixCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(ModXCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(LavarelCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(TildaCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(VamShopCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(NuxtCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(MagentoCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(OpenCardCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(InSalesCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(VigboCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(RubyOnRailsCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(RubyOnRailsCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(VueCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(MogutaCheckProcessor.class);
        processorCheckMultibinder.addBinding().to(ReactCheckProcessor.class);

        bind(new TypeLiteral<Determinant<CMSType, Destination>>(){}).to(CMSDeterminant.class);

        bind(Checker.class).annotatedWith(Cms.class).to(CMSChecker.class);

        bind(Checker.class).annotatedWith(Env.class).to(EnvironmentChecker.class);

        bind(Runner.class);
    }

}
