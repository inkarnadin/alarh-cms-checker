package web.cms;

import web.Connector;
import web.Params;

public abstract class AbstractCMSConnector implements Connector {

    protected Params params;

    @Override
    public void configure(Params params) {
        this.params = params;
    }

}
