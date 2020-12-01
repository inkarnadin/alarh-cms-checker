package web.struct;

import kotlin.Pair;
import web.analyzer.Importance;
import web.cms.CMSType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static web.analyzer.Importance.UNDEFINED;

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

}
