package web.cms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.cms.board.ips.IPSConnector;
import web.cms.classic.bitrix.BitrixConnector;
import web.cms.framework.codeigniter.CodeIgniterConnector;
import web.cms.classic.datalife.DataLifeConnector;
import web.cms.classic.drupal.DrupalConnector;
import web.cms.classic.host.HostCmsConnector;
import web.cms.construct.hugo.HugoConnector;
import web.cms.classic.image.ImageConnector;
import web.cms.construct.insales.InSalesConnector;
import web.cms.classic.joomla.JoomlaConnector;
import web.cms.framework.expressjs.ExpressJsConnector;
import web.cms.framework.lavarel.LavarelConnector;
import web.cms.classic.magento.MagentoConnector;
import web.cms.classic.maxsite.MaxSiteConnector;
import web.cms.classic.modx.ModXConnector;
import web.cms.classic.moguta.MogutaConnector;
import web.cms.framework.nodejs.NodeJsConnector;
import web.cms.framework.nuxt.NuxtConnector;
import web.cms.classic.opencart.OpenCartConnector;
import web.cms.classic.prestashop.PrestaShopConnector;
import web.cms.framework.rails.RubyOnRailsConnector;
import web.cms.framework.react.ReactConnector;
import web.cms.classic.shopify.ShopifyConnector;
import web.cms.construct.tilda.TildaConnector;
import web.cms.construct.ukit.UkitConnector;
import web.cms.classic.umi.UmiConnector;
import web.cms.classic.vamshop.VamShopConnector;
import web.cms.construct.vigbo.VigboConnector;
import web.cms.framework.vue.VueConnector;
import web.cms.construct.wix.WixConnector;
import web.cms.classic.wordpress.WordPressConnector;
import web.cms.framework.yii.YiiConnector;
import web.struct.Connector;

@Getter
@RequiredArgsConstructor
public enum CMSType {

    // классические CMS
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
    PRESTA_SHOP             (115, "PrestaShop",       PrestaShopConnector.class),

    // фреймворки
    LAVAREL                 (201, "Lavarel",          LavarelConnector.class),
    YII                     (202, "Yii Framework",    YiiConnector.class),
    NUXT_JS                 (203, "Nuxt.js",          NuxtConnector.class),
    VUE_JS                  (204, "Vue.js",           VueConnector.class),
    RUBY_ON_RAILS           (205, "Ruby on Rails",    RubyOnRailsConnector.class),
    REACT_JS                (206, "ReactJS",          ReactConnector.class),
    CODE_IGNITER            (207, "CodeIgniter",      CodeIgniterConnector.class),
    NODE_JS                 (208, "NodeJS",           NodeJsConnector.class),
    EXPRESS_JS              (209, "ExpressJs",        ExpressJsConnector.class),

    // конструкторы
    TILDA                   (301, "Tilda",            TildaConnector.class),
    VIGBO                   (302, "Vigbo CMS",        VigboConnector.class),
    INSALES                 (303, "InSales",          InSalesConnector.class),
    UKIT                    (304, "uKit",             UkitConnector.class),
    WIX                     (305, "Wix",              WixConnector.class),
    HUGO                    (306, "Hugo",             HugoConnector .class),

    // форумные движки
    IPS                     (401, "IP.Board",         IPSConnector.class);

    private final int id;
    private final String name;
    private final Class<? extends Connector> connector;

}
