package web.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Destination {

    Map<Integer, String> result = new HashMap<>();

    default void insert(Integer index, String row) {
        result.put(index, row);
    }

    default List<String> fetch() {
        return new ArrayList<>(result.values());
    }

    default Boolean isFull() {
        return result.size() > 0;
    }

}