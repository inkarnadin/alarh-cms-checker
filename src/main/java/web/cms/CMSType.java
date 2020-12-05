package web.cms;

import com.google.inject.Provider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.cms.bitrix.annotation.Bitrix;
import web.cms.datalife.annotation.DataLife;
import web.cms.drupal.annotation.Drupal;
import web.cms.insales.annotation.InSales;
import web.cms.joomla.annotation.Joomla;
import web.cms.lavarel.annotation.Lavarel;
import web.cms.magento.annotation.Magento;
import web.cms.maxsite.annotation.MaxSite;
import web.cms.modx.annotation.ModX;
import web.cms.nuxt.annotation.Nuxt;
import web.cms.opencart.annotation.OpenCart;
import web.cms.rails.annotation.RubyOnRails;
import web.cms.tilda.annotation.Tilda;
import web.cms.vamshop.annotation.VamShop;
import web.cms.vigbo.annotation.Vigbo;
import web.cms.vue.annotation.Vue;
import web.cms.wordpress.annotation.WordPress;
import web.cms.yii.annotation.Yii;
import web.module.provider.*;
import web.struct.Connector;

@Getter
@RequiredArgsConstructor
public enum CMSType {

    WORDPRESS               (0, "WordPress",        WordPressProvider.class),
    JOOMLA                  (1, "Joomla!",          JoomlaProvider.class),
    DATALIFE_ENGINE         (2, "DataLife Engine",  DataLifeProvider.class),
    MAXSITE_CMS             (3, "MaxSite CMS",      MaxSiteProvider.class),
    DRUPAL                  (4, "Drupal",           DrupalProvider.class),
    BITRIX                  (5, "1C-Bitrix",        BitrixProvider.class),
    MODX                    (6, "MODx",             ModXProvider.class),
    VAM_SHOP                (7, "VamShop",          VamShopProvider.class),
    MAGENTO                 (8, "Magento",          MagentoProvider.class),
    OPENCART                (9, "OpenCart",         OpenCartProvider.class),

    LAVAREL                 (10, "Lavarel",         LavarelProvider.class),
    YII                     (11, "Yii Framework",   YiiProvider.class),
    NUXT_JS                 (12, "Nuxt.js",         NuxtProvider.class),
    VUE_JS                  (13, "Vue.js",          VueProvider.class),
    RUBY_ON_RAILS           (14, "Ruby on Rails",   RubyOnRailsProvider.class),

    TILDA                   (15, "Tilda",           TildaProvider.class),
    VIGBO                   (16, "Vigbo CMS",       VigboProvider.class),
    INSALES                 (17, "InSales",         InSalesProvider.class);

    private final int id;
    private final String name;
    private final Class<? extends Provider<Connector>> provider;

}
