package web.analyzer;

import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * Статическое хранилище атрибутов метаданных "Дублинского ядра".
 * <p>В некоторых CMS встроено по умолчанию и может служить косвенным признаком использования.
 *
 * @author inkarnadin
 * on 28-12-2020
 */
@UtilityClass
public class DublinCoreSignStorage implements SignStorage {

    @Getter
    private static final Pattern[] elements = new Pattern[] {
            Pattern.compile("DC\\.Title"),
            Pattern.compile("DC\\.Creator"),
            Pattern.compile("DC\\.Subject"),
            Pattern.compile("DC\\.Description"),
            Pattern.compile("DC\\.Contributor"),
            Pattern.compile("DC\\.Date"),
            Pattern.compile("DC\\.Type"),
            Pattern.compile("DC\\.Format"),
            Pattern.compile("DC\\.Identifier"),
            Pattern.compile("DC\\.Source"),
            Pattern.compile("DC\\.Language"),
            Pattern.compile("DC\\.Relation"),
            Pattern.compile("DC\\.Coverage"),
            Pattern.compile("DC\\.Rights")
    };

}
