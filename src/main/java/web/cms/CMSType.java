package web.cms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.cms.bitrix.BitrixConnector;
import web.cms.datalife.DataLifeConnector;
import web.cms.drupal.DrupalConnector;
import web.cms.host.HostCmsConnector;
import web.cms.image.ImageConnector;
import web.cms.insales.InSalesConnector;
import web.cms.joomla.JoomlaConnector;
import web.cms.lavarel.LavarelConnector;
import web.cms.magento.MagentoConnector;
import web.cms.maxsite.MaxSiteConnector;
import web.cms.modx.ModXConnector;
import web.cms.moguta.MogutaConnector;
import web.cms.nuxt.NuxtConnector;
import web.cms.opencart.OpenCartConnector;
import web.cms.rails.RubyOnRailsConnector;
import web.cms.react.ReactConnector;
import web.cms.shopify.ShopifyConnector;
import web.cms.tilda.TildaConnector;
import web.cms.ukit.UkitConnector;
import web.cms.umi.UmiConnector;
import web.cms.vamshop.VamShopConnector;
import web.cms.vigbo.VigboConnector;
import web.cms.vue.VueConnector;
import web.cms.wix.WixConnector;
import web.cms.wordpress.WordPressConnector;
import web.cms.yii.YiiConnector;
import web.struct.Connector;

@Getter
@RequiredArgsConstructor
public enum CMSType {

    WORDPRESS               (100, "WordPress",        WordPressConnector.class),
    JOOMLA                  (101, "Joomla!",          JoomlaConnector.class),
    DATALIFE_ENGINE         (102, "DataLife Engine",  DataLifeConnector.class),
    MAXSITE_CMS             (103, "MaxSite CMS",      MaxSiteConnector.class),
    DRUPAL                  (104, "Drupal",           DrupalConnector.class),
    BITRIX                  (105, "1C-Bitrix",        BitrixConnector.class),
    MODX                    (106, "MODx",             ModXConnector.class),
    VAM_SHOP                (107, "VamShop",          VamShopConnector.class),
    MAGENTO                 (108, "Magento",          MagentoConnector.class),
    OPENCART                (109, "OpenCart",         OpenCartConnector.class),
    MOGUTA_CMS              (110, "Moguta.CMS",       MogutaConnector.class),
    SHOPIFY                 (111, "Shopify",          ShopifyConnector.class),
    HOST_CMS                (112, "HostCMS",          HostCmsConnector.class),
    UMI_CMS                 (113, "UMI.CMS",          UmiConnector.class),
    IMAGE_CMS               (114, "ImageCMS",         ImageConnector.class),

    LAVAREL                 (201, "Lavarel",          LavarelConnector.class),
    YII                     (202, "Yii Framework",    YiiConnector.class),
    NUXT_JS                 (203, "Nuxt.js",          NuxtConnector.class),
    VUE_JS                  (204, "Vue.js",           VueConnector.class),
    RUBY_ON_RAILS           (205, "Ruby on Rails",    RubyOnRailsConnector.class),
    REACT_JS                (206, "ReactJS",          ReactConnector.class),

    TILDA                   (301, "Tilda",            TildaConnector.class),
    VIGBO                   (302, "Vigbo CMS",        VigboConnector.class),
    INSALES                 (303, "InSales",          InSalesConnector.class),
    UKIT                    (304, "uKit",             UkitConnector.class),
    WIX                     (305, "Wix",              WixConnector.class);

    private final int id;
    private final String name;
    private final Class<? extends Connector> connector;

}
