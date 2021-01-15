package web.cms;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.SneakyThrows;
import web.struct.*;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CMSDeterminant implements Determinant<CMSType, Destination> {

    @Inject
    private Set<Connector> connectors;

    @Override
    @SneakyThrows
    public Map<CMSType, Destination> define(Params params) {
        Map<CMSType, Destination> result = new HashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        HashSet<Determinative> callables = new HashSet<>();

        connectors.forEach(c -> callables.add(new Determinative(c, params)));

        List<Future<Pair<CMSType, Optional<Destination>>>> futures = executorService.invokeAll(callables);
        for (Future<Pair<CMSType, Optional<Destination>>> future : futures) {
            Pair<CMSType, Optional<Destination>> pair = future.get();
            pair.getSecond().ifPresent(x -> result.put(pair.getFirst(), x));
        }
        return result;
    }

    static class Determinative implements Callable<Pair<CMSType, Optional<Destination>>> {

        private final Connector connector;

        Determinative(Connector connector, Params params) {
            this.connector = connector;
            this.connector.configure(params);
        }

        @Override
        public Pair<CMSType, Optional<Destination>> call() {
            return connector.check();
        }
    }

}
