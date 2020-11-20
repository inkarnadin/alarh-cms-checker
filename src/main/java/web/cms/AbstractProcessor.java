package web.cms;

import web.Processor;

import java.util.Objects;

public class AbstractProcessor implements Processor {

    protected String protocol;
    protected String url;

    @Override
    public void configure(String protocol, String url) {
        Objects.requireNonNull(protocol, "Empty protocol value!");
        Objects.requireNonNull(url, "Empty url value!");

        this.protocol = protocol;
        this.url = url;
    }

    @Override
    public void process() {

    }

}
