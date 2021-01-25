package web.cms;

import kotlin.Pair;
import web.http.Host;
import web.struct.Destination;
import web.struct.Processor;
import web.struct.assignment.DefaultAssigner;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractCMSProcessor implements Processor<CMSType>, DefaultAssigner {

    protected final Map<String, Integer> errorMap = new HashMap<>();

    protected String protocol;
    protected String server;
    protected Host host;

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
    public Pair<CMSType, Optional<Destination>> transmit() {
        return null;
    }

}
