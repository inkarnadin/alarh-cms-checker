package web.cms;

import web.Source;

import java.util.ArrayList;
import java.util.List;

public class AbstractSource implements Source {

    protected final List<String> sources = new ArrayList<>();

    @Override
    public List<String> getSources() {
        return sources;
    }

}
