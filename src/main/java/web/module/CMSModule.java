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
import web.cms.board.ips.IPSCheckProcessor;
import web.cms.board.ips.IPSConnector;
import web.cms.classic.bitrix.BitrixCheckProcessor;
import web.cms.classic.bitrix.BitrixConnector;
import web.cms.classic.bitrix.BitrixVersionProcessor;
import web.cms.classic.joomla.JoomlaCheckProcessor;
import web.cms.classic.joomla.JoomlaConnector;
import web.cms.classic.joomla.JoomlaExtensionSource;
import web.cms.classic.joomla.JoomlaPluginsProcessor;
import web.cms.classic.joomla.JoomlaVersionProcessor;
import web.cms.classic.wordpress.WordPressCheckProcessor;
import web.cms.classic.wordpress.WordPressConnector;
import web.cms.classic.wordpress.WordPressPluginExtractor;
import web.cms.classic.wordpress.WordPressPluginProcessor;
import web.cms.classic.wordpress.WordPressThemeExtractor;
import web.cms.classic.wordpress.WordPressThemeProcessor;
import web.cms.classic.wordpress.WordPressThemeSource;
import web.cms.classic.wordpress.WordPressVersionProcessor;
import web.cms.classic.wordpress.WordPressVersionValidator;
import web.cms.framework.codeigniter.CodeIgniterCheckProcessor;
import web.cms.framework.codeigniter.CodeIgniterConnector;
import web.cms.classic.datalife.DataLifeCheckProcessor;
import web.cms.classic.datalife.DataLifeConnector;
import web.cms.classic.datalife.DataLifeVersionProcessor;
import web.cms.classic.drupal.DrupalCheckProcessor;
import web.cms.classic.drupal.DrupalConnector;
import web.cms.classic.drupal.DrupalVersionProcessor;
import web.cms.classic.host.HostCmsCheckProcessor;
import web.cms.classic.host.HostCmsConnector;
import web.cms.construct.hugo.HugoCheckProcessor;
import web.cms.construct.hugo.HugoConnector;
import web.cms.construct.hugo.HugoVersionProcessor;
import web.cms.classic.image.ImageCmsCheckProcessor;
import web.cms.classic.image.ImageConnector;
import web.cms.construct.insales.InSalesCheckProcessor;
import web.cms.construct.insales.InSalesConnector;
import web.cms.framework.lavarel.LavarelCheckProcessor;
import web.cms.framework.lavarel.LavarelConnector;
import web.cms.classic.magento.MagentoCheckProcessor;
import web.cms.classic.magento.MagentoConnector;
import web.cms.classic.maxsite.MaxSiteCheckProcessor;
import web.cms.classic.maxsite.MaxSiteConnector;
import web.cms.classic.modx.ModXCheckProcessor;
import web.cms.classic.modx.ModXConnector;
import web.cms.classic.modx.ModXVersionProcessor;
import web.cms.classic.moguta.MogutaCheckProcessor;
import web.cms.classic.moguta.MogutaConnector;
import web.cms.classic.moguta.MogutaVersionProcessor;
import web.cms.framework.nuxt.NuxtCheckProcessor;
import web.cms.framework.nuxt.NuxtConnector;
import web.cms.classic.opencart.OpenCardCheckProcessor;
import web.cms.classic.opencart.OpenCartConnector;
import web.cms.classic.prestashop.PrestaShopCheckProcessor;
import web.cms.classic.prestashop.PrestaShopConnector;
import web.cms.framework.rails.RubyOnRailsCheckProcessor;
import web.cms.framework.rails.RubyOnRailsConnector;
import web.cms.framework.react.ReactCheckProcessor;
import web.cms.framework.react.ReactConnector;
import web.cms.framework.react.ReactVersionProcessor;
import web.cms.classic.shopify.ShopifyCheckProcessor;
import web.cms.classic.shopify.ShopifyConnector;
import web.cms.construct.tilda.TildaCheckProcessor;
import web.cms.construct.tilda.TildaConnector;
import web.cms.construct.ukit.UkitCheckProcessor;
import web.cms.construct.ukit.UkitConnector;
import web.cms.classic.umi.UmiCheckProcessor;
import web.cms.classic.umi.UmiConnector;
import web.cms.classic.umi.UmiVersionProcessor;
import web.cms.classic.vamshop.VamShopCheckProcessor;
import web.cms.classic.vamshop.VamShopConnector;
import web.cms.construct.vigbo.VigboCheckProcessor;
import web.cms.construct.vigbo.VigboConnector;
import web.cms.framework.vue.VueCheckProcessor;
import web.cms.framework.vue.VueConnector;
import web.cms.framework.vue.VueVersionProcessor;
import web.cms.construct.wix.WixCheckProcessor;
import web.cms.construct.wix.WixConnector;
import web.cms.framework.yii.YiiCheckProcessor;
import web.cms.framework.yii.YiiConnector;
import web.cms.framework.yii.YiiVersionProcessor;
import web.struct.*;
import web.struct.ResultContainer;

import static web.cms.CMSMarker.BITRIX_CHECK;
import static web.cms.CMSMarker.BITRIX_VERSION;
import static web.cms.CMSMarker.CODE_IGNITER_CHECK;
import static web.cms.CMSMarker.DATALIFE_CHECK;
import static web.cms.CMSMarker.DATALIFE_VERSION;
import static web.cms.CMSMarker.DRUPAL_CHECK;
import static web.cms.CMSMarker.DRUPAL_VERSION;
import static web.cms.CMSMarker.HOST_CHECK;
import static web.cms.CMSMarker.HUGO_CHECK;
import static web.cms.CMSMarker.HUGO_VERSION;
import static web.cms.CMSMarker.IMAGE_CHECK;
import static web.cms.CMSMarker.INSALES_CHECK;
import static web.cms.CMSMarker.IPS_CHECK;
import static web.cms.CMSMarker.JOOMLA_CHECK;
import static web.cms.CMSMarker.JOOMLA_PLUGIN;
import static web.cms.CMSMarker.JOOMLA_VERSION;
import static web.cms.CMSMarker.LAVAREL_CHECK;
import static web.cms.CMSMarker.MAGENTO_CHECK;
import static web.cms.CMSMarker.MAXSITE_CHECK;
import static web.cms.CMSMarker.MODX_CHECK;
import static web.cms.CMSMarker.MODX_VERSION;
import static web.cms.CMSMarker.MOGUTA_CHECK;
import static web.cms.CMSMarker.MOGUTA_VERSION;
import static web.cms.CMSMarker.NUXT_CHECK;
import static web.cms.CMSMarker.OPENCART_CHECK;
import static web.cms.CMSMarker.PRESTASHOP_CHECK;
import static web.cms.CMSMarker.REACT_CHECK;
import static web.cms.CMSMarker.REACT_VERSION;
import static web.cms.CMSMarker.RUBY_ON_RAILS_CHECK;
import static web.cms.CMSMarker.SHOPIFY_CHECK;
import static web.cms.CMSMarker.TILDA_CHECK;
import static web.cms.CMSMarker.UKIT_CHECK;
import static web.cms.CMSMarker.UMI_CHECK;
import static web.cms.CMSMarker.UMI_VERSION;
import static web.cms.CMSMarker.VAMSHOP_CHECK;
import static web.cms.CMSMarker.VIGBO_CHECK;
import static web.cms.CMSMarker.VUE_CHECK;
import static web.cms.CMSMarker.VUE_VERSION;
import static web.cms.CMSMarker.WIX_CHECK;
import static web.cms.CMSMarker.WORDPRESS_CHECK;
import static web.cms.CMSMarker.WORDPRESS_PLUGIN;
import static web.cms.CMSMarker.WORDPRESS_THEME;
import static web.cms.CMSMarker.WORDPRESS_VERSION;
import static web.cms.CMSMarker.YII_CHECK;
import static web.cms.CMSMarker.YII_VERSION;

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

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(HUGO_CHECK)).to(HugoCheckProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(HUGO_VERSION)).to(HugoVersionProcessor.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(Names.named(IPS_CHECK)).to(IPSCheckProcessor.class);

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
        connectorMultibinder.addBinding().to(HugoConnector.class);

        connectorMultibinder.addBinding().to(IPSConnector.class);

        bind(new TypeLiteral<Determinant<CMSType, ResultContainer>>(){}).to(CMSDeterminant.class);
    }

}
