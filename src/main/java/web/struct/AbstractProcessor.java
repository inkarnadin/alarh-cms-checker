package web.struct;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractProcessor implements Processor {

    protected final Map<String, Integer> errorMap = new HashMap<>();

    protected String protocol;
    protected String host;

    @Override
    public void configure(String protocol, String host) {
        Objects.requireNonNull(protocol, "Empty protocol value!");
        Objects.requireNonNull(host, "Empty url value!");

        this.protocol = protocol;
        this.host = host;
    }

    @Override
    public void process() {}

}
