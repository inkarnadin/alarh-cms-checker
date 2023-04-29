package web.struct;

import web.analyzer.Importance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Контейнер, содержащий результат анализа.
 *
 * @author inkarnadin
 * on 25-11-2020
 */
public class BaseResultContainer implements ResultContainer {

    protected Importance importance;
    protected Map<Integer, String> result = new HashMap<>();

    /**
     * Установить уровень важности результирующей строке. Важность перезаписывается в случае, если найден больший по порядку
     * уровень важности.
     *
     * @param importance важность
     * @see Importance
     */
    @Override
    public void setImportance(Importance importance) {
        if (Objects.isNull(this.importance) || importance.ordinal() > this.importance.ordinal()) {
            this.importance = importance;
        }
    }

    /**
     * Метод получения текущего состояния важности результата.
     *
     * @return важность
     */
    @Override
    public Importance getImportance() {
        return importance;
    }

    /**
     * Метод добавления нового результато анализа в контейнер.
     *
     * @param index номер по порядку
     * @param row   значение
     */
    @Override
    public void insert(Integer index, String row) {
        result.put(index, row);
    }

    /**
     * Метод получения всех доступных результатов анализа из контейнера.
     *
     * @return список результатов
     */
    @Override
    public List<String> fetch() {
        return new ArrayList<>(result.values());
    }

    /**
     * Метод проверки состояния контейнера на наличие результатов.
     *
     * @return состояние, если {@code true} - результат проверки положительный, иначе {@code false}
     */
    @Override
    public boolean isSuccess() {
        return result.size() > 0;
    }

}
