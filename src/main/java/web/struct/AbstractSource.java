package web.struct;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSource implements Source {

    protected final List<String> sources = new ArrayList<>();

    @Override
    public List<String> getSources() {
        return sources;
    }

}
