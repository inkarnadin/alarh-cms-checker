package web.analyzer;

import lombok.Builder;
import lombok.Getter;

/**
 * Класс-обертка для представления разбора результатов анализа встроенных скриптов.
 * <p>Включает в себя список путей до найденных скриптов и признак измененного базового пути.
 *
 * @author inkarnadin
 * on 29-11-2020
 */
@Getter
@Builder(toBuilder = true)
public class DissectorResult {

    private final String[] paths;
    private final boolean isOverWrittenBasePath;

}