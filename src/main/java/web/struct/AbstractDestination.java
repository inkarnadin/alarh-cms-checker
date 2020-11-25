package web.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDestination implements Destination {

    Map<Integer, String> result = new HashMap<>();

    public void insert(Integer index, String row) {
        result.put(index, row);
    }

    public List<String> fetch() {
        return new ArrayList<>(result.values());
    }

    public Boolean isFull() {
        return result.size() > 0;
    }

}
