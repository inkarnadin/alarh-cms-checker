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
    UNKNOWN(-1, "Unknown");

    private final int id;
    private final String name;

}
