package web.struct;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractProcessor implements Processor {

    protected final Map<String, Integer> errorMap = new HashMap<>();

    protected String protocol;
    protected String server;

    protected AtomicInteger attempt = new AtomicInteger(0);
    protected AtomicInteger successAttempt = new AtomicInteger(0);

    protected final static String successMessage = "  * %s tags have been found (%s/%s)";

    @Override
    public void configure(String protocol, String server) {
        Objects.requireNonNull(protocol, "Empty protocol value!");
        Objects.requireNonNull(server, "Empty url value!");

        this.protocol = protocol;
        this.server = server;
    }

    @Override
    public void process() {}

}
