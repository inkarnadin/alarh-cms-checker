package web.cms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CMSType {

    WORDPRESS(0, "WordPress"),
    JOOMLA(1, "Joomla!"),
    DATALIFE_ENGINE(2, "DataLife Engine"),
    MAXSITE_CMS(3, "MaxSite CMS"),
    DRUPAL(4, "Drupal"),
    BITRIX(5, "1C-Bitrix"),
    MODX(6, "MODx"),
    VAM_SHOP(7, "VamShop"),
    MAGENTO(8, "Magento"),
    OPENCART(9, "OpenCart"),

    LAVAREL(10, "Lavarel"),
    YII(11, "Yii Framework"),
    NUXT_JS(12, "Nuxt.js"),
    RUBY_ON_RAILS(13, "Ruby on Rails"),

    TILDA(14, "Tilda"),
    VIGBO(15, "Vigbo CMS"),
    INSALES(16, "InSales"),

    UNKNOWN(-1, "Unknown");

    private final int id;
    private final String name;

}
