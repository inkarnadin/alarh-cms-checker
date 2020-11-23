package web.db;

import web.struct.Connector;
import web.struct.Params;

public abstract class AbstractDBConnector implements Connector {

    protected Params params;

    @Override
    public void configure(Params params) {
        this.params = params;
    }

}
