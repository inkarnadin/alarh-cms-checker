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

    WORDPRESS               (0, "WordPress",        WordPressProvider.class,    WordPress.class),
    JOOMLA                  (1, "Joomla!",          JoomlaProvider.class,       Joomla.class),
    DATALIFE_ENGINE         (2, "DataLife Engine",  DataLifeProvider.class,     DataLife.class),
    MAXSITE_CMS             (3, "MaxSite CMS",      MaxSiteProvider.class,      MaxSite.class),
    DRUPAL                  (4, "Drupal",           DrupalProvider.class,       Drupal.class),
    BITRIX                  (5, "1C-Bitrix",        BitrixProvider.class,       Bitrix.class),
    MODX                    (6, "MODx",             ModXProvider.class,         ModX.class),
    VAM_SHOP                (7, "VamShop",          VamShopProvider.class,      VamShop.class),
    MAGENTO                 (8, "Magento",          MagentoProvider.class,      Magento.class),
    OPENCART                (9, "OpenCart",         OpenCartProvider.class,     OpenCart.class),

    LAVAREL                 (10, "Lavarel",         LavarelProvider.class,      Lavarel.class),
    YII                     (11, "Yii Framework",   YiiProvider.class,          Yii.class),
    NUXT_JS                 (12, "Nuxt.js",         NuxtProvider.class,         Nuxt.class),
    VUE_JS                  (13, "Vue.js",          VueProvider.class,          Vue.class),
    RUBY_ON_RAILS           (14, "Ruby on Rails",   RubyOnRailsProvider.class,  RubyOnRails.class),

    TILDA                   (15, "Tilda",           TildaProvider.class,        Tilda.class),
    VIGBO                   (16, "Vigbo CMS",       VigboProvider.class,        Vigbo.class),
    INSALES                 (17, "InSales",         InSalesProvider.class,      InSales.class);

    private final int id;
    private final String name;
    private final Class<? extends Provider<Connector>> provider;
    private final Class<?> marker;

    public static CMSType search(Class<?> clazz) {
        for (CMSType cmsType : values()) {
            Class<?> marker = cmsType.getMarker();
            if (marker == clazz)
                return cmsType;
        }
        throw new IllegalArgumentException("Wrong CMS type!");
    }

}
