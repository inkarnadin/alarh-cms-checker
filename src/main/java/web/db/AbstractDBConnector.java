package web.db;

import web.Connector;

public abstract class AbstractDBConnector implements Connector {

    protected String protocol;
    protected String host;

    @Override
    public void configure(String protocol, String host) {
        this.protocol = protocol;
        this.host = host;
    }

}
