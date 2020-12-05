package web.cms;

import kotlin.Pair;
import web.analyzer.Importance;
import web.cms.CMSType;
import web.struct.Destination;
import web.struct.Processor;

import java.util.*;

import static web.analyzer.Importance.UNDEFINED;

public abstract class AbstractCMSProcessor implements Processor<CMSType> {

    protected final Map<String, Integer> errorMap = new HashMap<>();

    protected String protocol;
    protected String server;

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

    protected void assign(Destination destination, List<Pair<Boolean, Importance>> result, CMSType cmsType) {
        long count = result.stream().filter(Pair::getFirst).count();
        Importance max = result.stream()
                .filter(Pair::getFirst)
                .map(Pair::getSecond)
                .max(Importance::compareTo).orElse(UNDEFINED);

        if (count > 0) {
            destination.setImportance(max);
            destination.insert(0, String.format(
                    successMessage,
                    cmsType.getName(),
                    count,
                    result.size())
            );
        }
    }

    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return null;
    }

}
