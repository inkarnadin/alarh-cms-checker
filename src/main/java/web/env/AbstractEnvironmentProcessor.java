package web.env;

import kotlin.Pair;
import web.analyzer.Importance;
import web.cms.CMSType;
import web.http.Host;
import web.struct.Destination;
import web.struct.Processor;

import java.util.*;

import static web.analyzer.Importance.UNDEFINED;

public abstract class AbstractEnvironmentProcessor implements Processor<EnvType> {

    protected final Map<String, Integer> errorMap = new HashMap<>();

    protected String protocol;
    protected String server;

    protected Host host;

    protected final static String successMessage = "  * %s tags have been found (%s/%s)";

    @Override
    public void configure(String protocol, String server) {
        Objects.requireNonNull(protocol, "Empty protocol value!");
        Objects.requireNonNull(server, "Empty url value!");

        this.protocol = protocol;
        this.server = server;

        this.host = new Host(protocol, server);
    }

    @Override
    public void process() {}

    @Override
    public Pair<EnvType, Optional<Destination>> transmit() {
        return null;
    }

}
