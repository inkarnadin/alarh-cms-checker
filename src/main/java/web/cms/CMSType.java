package web.cms;

import com.google.inject.Provider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.module.provider.*;
import web.struct.Connector;

@Getter
@RequiredArgsConstructor
public enum CMSType {

    WORDPRESS               (100, "WordPress",        WordPressProvider.class),
    JOOMLA                  (101, "Joomla!",          JoomlaProvider.class),
    DATALIFE_ENGINE         (102, "DataLife Engine",  DataLifeProvider.class),
    MAXSITE_CMS             (103, "MaxSite CMS",      MaxSiteProvider.class),
    DRUPAL                  (104, "Drupal",           DrupalProvider.class),
    BITRIX                  (105, "1C-Bitrix",        BitrixProvider.class),
    MODX                    (106, "MODx",             ModXProvider.class),
    VAM_SHOP                (107, "VamShop",          VamShopProvider.class),
    MAGENTO                 (108, "Magento",          MagentoProvider.class),
    OPENCART                (109, "OpenCart",         OpenCartProvider.class),
    MOGUTA_CMS              (110, "Moguta.CMS",       MogutaProvider.class),
    SHOPIFY                 (111, "Shopify",          ShopifyProvider.class),
    HOST_CMS                (112, "HostCMS",          HostCmsProvider.class),
    UMI_CMS                 (113, "UMI.CMS",          UmiProvider.class),

    LAVAREL                 (201, "Lavarel",         LavarelProvider.class),
    YII                     (202, "Yii Framework",   YiiProvider.class),
    NUXT_JS                 (203, "Nuxt.js",         NuxtProvider.class),
    VUE_JS                  (204, "Vue.js",          VueProvider.class),
    RUBY_ON_RAILS           (205, "Ruby on Rails",   RubyOnRailsProvider.class),
    REACT_JS                (206, "ReactJS",         ReactProvider.class),

    TILDA                   (301, "Tilda",           TildaProvider.class),
    VIGBO                   (302, "Vigbo CMS",       VigboProvider.class),
    INSALES                 (303, "InSales",         InSalesProvider.class),
    UKIT                    (304, "uKit",            UkitProvider.class);

    private final int id;
    private final String name;
    private final Class<? extends Provider<Connector>> provider;

}
