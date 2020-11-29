package web.cms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CMSType {

    WORDPRESS(0, "WordPress"),
    JOOMLA(1, "Joomla!"),
    YII(2, "Yii Framework"),
    DATALIFE_ENGINE(3, "DataLife Engine"),
    MAXSITE_CMS(4, "MaxSite CMS"),
    DRUPAL(5, "Drupal"),
    BITRIX(6, "1C-Bitrix"),
    MODX(7, "MODx"),
    LAVAREL(8, "Lavarel"),
    UNKNOWN(-1, "Unknown");

    private final int id;
    private final String name;

}
