package web.struct.assignment;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.struct.ResultContainer;

import java.util.Collections;
import java.util.Set;

/**
 * Компонент анализа найденных версий для выявления актуальной.
 *
 * @author inkarnadin
 * on 23-01-2021
 */
public interface VersionAssigner {

    /**
     * Метод определения актуальной версии.
     * <p>Изначально в качестве основной выбирается максимальная определенная версия. Если эта версия маркирована символом "+", что
     * означает примерное определение, производится поиск аналогичной версии, добавленной в список какой-либо другой методикой без
     * символа "+". Если поиск подтверждает наличие такой версии в списке, версия считается определенной однозначно и будет возвращена.
     * В противном случае версия возвращается неуточненной, то есть, помеченной знаком "+".
     *
     * @param resultContainer объект, содержащий результат
     * @param versionSet список найденных версий
     */
    default void assign(ResultContainer resultContainer, Set<ComparableVersion> versionSet) {
        ComparableVersion maxVersion = Collections.max(versionSet);
        ComparableVersion refinedVersion = new ComparableVersion(maxVersion.toString().replace("+", ""));
        if (versionSet.contains(refinedVersion)) {
            maxVersion = refinedVersion;
        }
        resultContainer.insert(0, maxVersion.toString());
    }

}
