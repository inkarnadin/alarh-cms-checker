package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import web.analyzer.plugin.PluginObject;
import web.analyzer.theme.Extractor;
import web.analyzer.theme.ThemeObject;
import web.cms.CMSDeterminant;
import web.cms.CMSType;
import web.cms.bitrix.BitrixCheckProcessor;
import web.cms.bitrix.BitrixConnector;
import web.cms.bitrix.BitrixVersionProcessor;
import web.cms.codeigniter.CodeIgniterCheckProcessor;
import web.cms.codeigniter.CodeIgniterConnector;
import web.cms.datalife.DataLifeCheckProcessor;
import web.cms.datalife.DataLifeConnector;
import web.cms.datalife.DataLifeVersionProcessor;
import web.cms.drupal.DrupalCheckProcessor;
import web.cms.drupal.DrupalConnector;
import web.cms.drupal.DrupalVersionProcessor;
import web.cms.host.HostCmsCheckProcessor;
import web.cms.host.HostCmsConnector;
import web.cms.image.ImageCmsCheckProcessor;
import web.cms.image.ImageConnector;
import web.cms.insales.InSalesCheckProcessor;
import web.cms.insales.InSalesConnector;
import web.cms.joomla.*;
import web.cms.lavarel.LavarelCheckProcessor;
import web.cms.lavarel.LavarelConnector;
import web.cms.magento.MagentoCheckProcessor;
import web.cms.magento.MagentoConnector;
import web.cms.maxsite.MaxSiteCheckProcessor;
import web.cms.maxsite.MaxSiteConnector;
import web.cms.modx.ModXCheckProcessor;
import web.cms.modx.ModXConnector;
import web.cms.modx.ModXVersionProcessor;
import web.cms.moguta.MogutaCheckProcessor;
import web.cms.moguta.MogutaConnector;
import web.cms.moguta.MogutaVersionProcessor;
import web.cms.nuxt.NuxtCheckProcessor;
import web.cms.nuxt.NuxtConnector;
import web.cms.opencart.OpenCardCheckProcessor;
import web.cms.opencart.OpenCartConnector;
import web.cms.prestashop.PrestaShopCheckProcessor;
import web.cms.prestashop.PrestaShopConnector;
import web.cms.rails.RubyOnRailsCheckProcessor;
import web.cms.rails.RubyOnRailsConnector;
import web.cms.react.ReactCheckProcessor;
import web.cms.react.ReactConnector;
import web.cms.react.ReactVersionProcessor;
import web.cms.shopify.ShopifyCheckProcessor;
import web.cms.shopify.ShopifyConnector;
import web.cms.tilda.TildaCheckProcessor;
import web.cms.tilda.TildaConnector;
import web.cms.ukit.UkitCheckProcessor;
import web.cms.ukit.UkitConnector;
import web.cms.umi.UmiCheckProcessor;
import web.cms.umi.UmiConnector;
import web.cms.umi.UmiVersionProcessor;
import web.cms.vamshop.VamShopCheckProcessor;
import web.cms.vamshop.VamShopConnector;
import web.cms.vigbo.VigboCheckProcessor;
import web.cms.vigbo.VigboConnector;
import web.cms.vue.VueCheckProcessor;
import web.cms.vue.VueConnector;
import web.cms.vue.VueVersionProcessor;
import web.cms.wix.WixCheckProcessor;
import web.cms.wix.WixConnector;
import web.cms.wordpress.*;
import web.cms.yii.YiiCheckProcessor;
import web.cms.yii.YiiConnector;
import web.cms.yii.YiiVersionProcessor;
import web.struct.*;

import static web.cms.CMSMarker.*;

public class CMSModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new CommonModule());

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(WORDPRESS_CHECK)).to(WordPressCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(WORDPRESS_VERSION)).to(WordPressVersionProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(WORDPRESS_THEME)).to(WordPressThemeProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(WORDPRESS_PLUGIN)).to(WordPressPluginProcessor.class);
        bind(Source.class).annotatedWith(Names.named(WORDPRESS_THEME)).to(WordPressThemeSource.class);
        bind(Validator.class).annotatedWith(Names.named(WORDPRESS_VERSION)).to(WordPressVersionValidator.class);
        bind(new TypeLiteral<Extractor<ThemeObject>>(){}).annotatedWith(Names.named(WORDPRESS_THEME)).to(WordPressThemeExtractor.class);
        bind(new TypeLiteral<Extractor<PluginObject>>(){}).annotatedWith(Names.named(WORDPRESS_PLUGIN)).to(WordPressPluginExtractor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(JOOMLA_CHECK)).to(JoomlaCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(JOOMLA_VERSION)).to(JoomlaVersionProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(JOOMLA_PLUGIN)).to(JoomlaPluginsProcessor.class);
        bind(Source.class).annotatedWith(Names.named(JOOMLA_PLUGIN)).to(JoomlaExtensionSource.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(DATALIFE_CHECK)).to(DataLifeCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(DATALIFE_VERSION)).to(DataLifeVersionProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(MAXSITE_CHECK)).to(MaxSiteCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(DRUPAL_CHECK)).to(DrupalCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(DRUPAL_VERSION)).to(DrupalVersionProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(BITRIX_CHECK)).to(BitrixCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(BITRIX_VERSION)).to(BitrixVersionProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(MODX_CHECK)).to(ModXCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(MODX_VERSION)).to(ModXVersionProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(VAMSHOP_CHECK)).to(VamShopCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(MAGENTO_CHECK)).to(MagentoCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(OPENCART_CHECK)).to(OpenCardCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(MOGUTA_CHECK)).to(MogutaCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(MOGUTA_VERSION)).to(MogutaVersionProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(SHOPIFY_CHECK)).to(ShopifyCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(HOST_CHECK)).to(HostCmsCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(UMI_CHECK)).to(UmiCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(UMI_VERSION)).to(UmiVersionProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(IMAGE_CHECK)).to(ImageCmsCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(PRESTASHOP_CHECK)).to(PrestaShopCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(LAVAREL_CHECK)).to(LavarelCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(YII_CHECK)).to(YiiCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(YII_VERSION)).to(YiiVersionProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(NUXT_CHECK)).to(NuxtCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(VUE_CHECK)).to(VueCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(VUE_VERSION)).to(VueVersionProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(RUBY_ON_RAILS_CHECK)).to(RubyOnRailsCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(REACT_CHECK)).to(ReactCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(REACT_VERSION)).to(ReactVersionProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(CODE_IGNITER_CHECK)).to(CodeIgniterCheckProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(TILDA_CHECK)).to(TildaCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(VIGBO_CHECK)).to(VigboCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(INSALES_CHECK)).to(InSalesCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(UKIT_CHECK)).to(UkitCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(WIX_CHECK)).to(WixCheckProcessor.class);

        Multibinder<Connector> connectorMultibinder = Multibinder.newSetBinder(binder(), new TypeLiteral<>(){});
        connectorMultibinder.addBinding().to(WordPressConnector.class);
        connectorMultibinder.addBinding().to(JoomlaConnector.class);
        connectorMultibinder.addBinding().to(DataLifeConnector.class);
        connectorMultibinder.addBinding().to(MaxSiteConnector.class);
        connectorMultibinder.addBinding().to(DrupalConnector.class);
        connectorMultibinder.addBinding().to(BitrixConnector.class);
        connectorMultibinder.addBinding().to(ModXConnector.class);
        connectorMultibinder.addBinding().to(VamShopConnector.class);
        connectorMultibinder.addBinding().to(MagentoConnector.class);
        connectorMultibinder.addBinding().to(OpenCartConnector.class);
        connectorMultibinder.addBinding().to(MogutaConnector.class);
        connectorMultibinder.addBinding().to(ShopifyConnector.class);
        connectorMultibinder.addBinding().to(HostCmsConnector.class);
        connectorMultibinder.addBinding().to(UmiConnector.class);
        connectorMultibinder.addBinding().to(ImageConnector.class);
        connectorMultibinder.addBinding().to(PrestaShopConnector.class);

        connectorMultibinder.addBinding().to(LavarelConnector.class);
        connectorMultibinder.addBinding().to(YiiConnector.class);
        connectorMultibinder.addBinding().to(NuxtConnector.class);
        connectorMultibinder.addBinding().to(VueConnector.class);
        connectorMultibinder.addBinding().to(RubyOnRailsConnector.class);
        connectorMultibinder.addBinding().to(ReactConnector.class);
        connectorMultibinder.addBinding().to(CodeIgniterConnector.class);

        connectorMultibinder.addBinding().to(TildaConnector.class);
        connectorMultibinder.addBinding().to(VigboConnector.class);
        connectorMultibinder.addBinding().to(InSalesConnector.class);
        connectorMultibinder.addBinding().to(UkitConnector.class);
        connectorMultibinder.addBinding().to(WixConnector.class);

        bind(new TypeLiteral<Determinant<CMSType, Destination>>(){}).to(CMSDeterminant.class);
    }

}
