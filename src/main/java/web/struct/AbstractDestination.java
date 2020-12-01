package web.struct;

import web.analyzer.Importance;

import java.util.*;

public abstract class AbstractDestination implements Destination {

    protected Importance importance;
    protected Map<Integer, String> result = new HashMap<>();

    @Override
    public void setImportance(Importance importance) {
        if (Objects.isNull(this.importance) || importance.ordinal() > this.importance.ordinal())
            this.importance = importance;
    }

    @Override
    public Importance getImportance() {
        return importance;
    }

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
