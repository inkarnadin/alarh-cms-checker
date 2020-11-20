package web;

public class AbstractConnector implements Connector {

    protected String protocol;
    protected String host;

    @Override
    public void configure(String protocol, String host) {
        this.protocol = protocol;
        this.host = host;
    }

}
