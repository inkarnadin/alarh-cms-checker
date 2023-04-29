package web.cms;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.SneakyThrows;
import web.struct.Connector;
import web.struct.ResultContainer;
import web.struct.Determinant;
import web.struct.Params;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CMSDeterminant implements Determinant<CMSType, ResultContainer> {

    @Inject
    private Set<Connector> connectors;

    @Override
    @SneakyThrows
    public Map<CMSType, ResultContainer> define(Params params) {
        Map<CMSType, ResultContainer> result = new HashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        HashSet<Determinative> callables = new HashSet<>();

        connectors.forEach(c -> callables.add(new Determinative(c, params)));

        List<Future<Pair<CMSType, Optional<ResultContainer>>>> futures = executorService.invokeAll(callables);
        for (Future<Pair<CMSType, Optional<ResultContainer>>> future : futures) {
            Pair<CMSType, Optional<ResultContainer>> pair = future.get();
            pair.getSecond().ifPresent(x -> result.put(pair.getFirst(), x));
        }
        return result;
    }

    static class Determinative implements Callable<Pair<CMSType, Optional<ResultContainer>>> {

        private final Connector connector;

        Determinative(Connector connector, Params params) {
            this.connector = connector;
            this.connector.configure(params);
        }

        @Override
        public Pair<CMSType, Optional<ResultContainer>> call() {
            return connector.check();
        }
    }

}
