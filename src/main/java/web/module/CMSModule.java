package web.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import web.cms.CMSType;
import web.cms.bitrix.BitrixVersionProcessor;
import web.cms.bitrix.annotation.Bitrix;
import web.cms.bitrix.annotation.BitrixVersion;
import web.cms.datalife.DataLifeVersionProcessor;
import web.cms.datalife.annotation.DataLife;
import web.cms.datalife.annotation.DataLifeVersion;
import web.cms.drupal.DrupalVersionProcessor;
import web.cms.drupal.annotation.Drupal;
import web.cms.drupal.annotation.DrupalVersion;
import web.cms.host.annotation.HostCms;
import web.cms.insales.annotation.InSales;
import web.cms.joomla.JoomlaExtensionSource;
import web.cms.joomla.JoomlaPluginsProcessor;
import web.cms.joomla.JoomlaVersionProcessor;
import web.cms.joomla.annotation.Joomla;
import web.cms.joomla.annotation.JoomlaPlugin;
import web.cms.joomla.annotation.JoomlaVersion;
import web.cms.lavarel.annotation.Lavarel;
import web.cms.magento.annotation.Magento;
import web.cms.maxsite.annotation.MaxSite;
import web.cms.modx.annotation.ModX;
import web.cms.moguta.MogutaVersionProcessor;
import web.cms.moguta.annotation.Moguta;
import web.cms.moguta.annotation.MogutaVersion;
import web.cms.nuxt.annotation.Nuxt;
import web.cms.opencart.annotation.OpenCart;
import web.cms.react.annotation.React;
import web.cms.shopify.annotation.Shopify;
import web.cms.tilda.annotation.Tilda;
import web.cms.ukit.annotation.Ukit;
import web.cms.vamshop.annotation.VamShop;
import web.cms.vigbo.annotation.Vigbo;
import web.cms.vue.annotation.Vue;
import web.cms.wordpress.WordPressExtensionSource;
import web.cms.wordpress.WordPressPluginProcessor;
import web.cms.wordpress.WordPressVersionProcessor;
import web.cms.wordpress.annotation.WordPress;
import web.cms.wordpress.annotation.WordPressPlugin;
import web.cms.wordpress.annotation.WordPressVersion;
import web.cms.yii.annotation.Yii;
import web.http.Client;
import web.http.GetRequest;
import web.http.HttpClient;
import web.http.Request;
import web.module.provider.*;
import web.parser.StringReturnTextParser;
import web.parser.TextParser;
import web.parser.VersionXMLParser;
import web.parser.XMLParser;
import web.struct.*;

public class CMSModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Client.class).to(HttpClient.class);
        bind(Request.class).to(GetRequest.class);
        bind(Destination.class).to(SimpleDestination.class);

        bind(new TypeLiteral<TextParser<String>>(){}).to(StringReturnTextParser.class);
        bind(new TypeLiteral<XMLParser<String>>(){}).to(VersionXMLParser.class);

        bind(Connector.class).annotatedWith(Lavarel.class).toProvider(LavarelProvider.class);
        bind(Connector.class).annotatedWith(Yii.class).toProvider(YiiProvider.class);
        bind(Connector.class).annotatedWith(MaxSite.class).toProvider(MaxSiteProvider.class);
        bind(Connector.class).annotatedWith(ModX.class).toProvider(ModXProvider.class);
        bind(Connector.class).annotatedWith(Tilda.class).toProvider(TildaProvider.class);
        bind(Connector.class).annotatedWith(VamShop.class).toProvider(VamShopProvider.class);
        bind(Connector.class).annotatedWith(Nuxt.class).toProvider(NuxtProvider.class);
        bind(Connector.class).annotatedWith(Magento.class).toProvider(MagentoProvider.class);
        bind(Connector.class).annotatedWith(OpenCart.class).toProvider(OpenCartProvider.class);
        bind(Connector.class).annotatedWith(InSales.class).toProvider(InSalesProvider.class);
        bind(Connector.class).annotatedWith(Vigbo.class).toProvider(VigboProvider.class);
        bind(Connector.class).annotatedWith(Vue.class).toProvider(VueProvider.class);
        bind(Connector.class).annotatedWith(Moguta.class).toProvider(MogutaProvider.class);
        bind(Connector.class).annotatedWith(React.class).toProvider(ReactProvider.class);
        bind(Connector.class).annotatedWith(Shopify.class).toProvider(ShopifyProvider.class);
        bind(Connector.class).annotatedWith(Ukit.class).toProvider(UkitProvider.class);
        bind(Connector.class).annotatedWith(HostCms.class).toProvider(HostCmsProvider.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(MogutaVersion.class).to(MogutaVersionProcessor.class);
        bind(Connector.class).annotatedWith(Moguta.class).toProvider(MogutaProvider.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(BitrixVersion.class).to(BitrixVersionProcessor.class);
        bind(Connector.class).annotatedWith(Bitrix.class).toProvider(BitrixProvider.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(DataLifeVersion.class).to(DataLifeVersionProcessor.class);
        bind(Connector.class).annotatedWith(DataLife.class).toProvider(DataLifeProvider.class);

        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(DrupalVersion.class).to(DrupalVersionProcessor.class);
        bind(Connector.class).annotatedWith(Drupal.class).toProvider(DrupalProvider.class);

        bind(Source.class).annotatedWith(JoomlaPlugin.class).to(JoomlaExtensionSource.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(JoomlaPlugin.class).to(JoomlaPluginsProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(JoomlaVersion.class).to(JoomlaVersionProcessor.class);
        bind(Connector.class).annotatedWith(Joomla.class).toProvider(JoomlaProvider.class);

        bind(Source.class).annotatedWith(WordPressPlugin.class).to(WordPressExtensionSource.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(WordPressPlugin.class).to(WordPressPluginProcessor.class);
        bind(new TypeLiteral<Processor<CMSType>>(){}).annotatedWith(WordPressVersion.class).to(WordPressVersionProcessor.class);
        bind(Connector.class).annotatedWith(WordPress.class).toProvider(WordPressProvider.class);
    }

}
