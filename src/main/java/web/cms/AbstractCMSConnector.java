package web.cms;

import web.Connector;

public abstract class AbstractCMSConnector implements Connector {

    protected String protocol;
    protected String host;

    @Override
    public void configure(String protocol, String host) {
        this.protocol = protocol;
        this.host = host;
    }

}
