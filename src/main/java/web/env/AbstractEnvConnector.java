package web.env;

import web.struct.Connector;
import web.struct.Params;

public abstract class AbstractEnvConnector implements Connector {

    protected Params params;

    @Override
    public void configure(Params params) {
        this.params = params;
    }

}
