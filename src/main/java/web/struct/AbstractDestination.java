package web.struct;

import web.analyzer.Importance;

import java.util.*;

public abstract class AbstractDestination implements Destination {

    protected Importance importance;
    protected Map<Integer, String> result = new HashMap<>();

    /**
     * Set the importance of the row of the result
     * HIGH and MEDIUM - major priority
     * LOW - minor priority
     *
     * @param importance importance of result row
     * @see Importance
     */
    @Override
    public void setImportance(Importance importance) {
        if (Objects.isNull(this.importance) || importance.ordinal() > this.importance.ordinal())
            this.importance = importance;
    }

    /**
     * Get the importance of the row of the result
     *
     * @return importance of result row
     */
    @Override
    public Importance getImportance() {
        return importance;
    }

    /**
     * Insert the new result into destination container
     *
     * @param index sequential number
     * @param row   result value
     */
    @Override
    public void insert(Integer index, String row) {
        result.put(index, row);
    }

    /**
     * Fetch all available results
     *
     * @return list of result
     */
    @Override
    public List<String> fetch() {
        return new ArrayList<>(result.values());
    }

    /**
     * Checking state of destination - contain value or is empty
     *
     * @return state
     */
    @Override
    public Boolean isFull() {
        return result.size() > 0;
    }

}
