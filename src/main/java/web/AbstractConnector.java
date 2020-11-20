package web;

public class AbstractConnector implements Connector {

    protected String protocol;
    protected String url;

    @Override
    public void configure(String protocol, String url) {
        this.protocol = protocol;
        this.url = url;
    }

}
