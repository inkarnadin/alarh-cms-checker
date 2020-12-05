package web.cms;

import com.google.inject.Inject;
import kotlin.Pair;
import lombok.SneakyThrows;
import web.struct.Destination;
import web.struct.Determinant;
import web.struct.Params;
import web.struct.Processor;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CMSDeterminant implements Determinant<CMSType, Destination> {

    @Inject
    private Set<Processor<CMSType>> checkProcessors;

    @Override
    @SneakyThrows
    public Map<CMSType, Destination> define(Params params) {
        Map<CMSType, Destination> result = new HashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        HashSet<Determinative> callables = new HashSet<>();

        checkProcessors.forEach(processor -> callables.add(new Determinative(processor, params)));

        List<Future<Pair<CMSType, Optional<Destination>>>> futures = executorService.invokeAll(callables);
        for (Future<Pair<CMSType, Optional<Destination>>> future : futures) {
            Pair<CMSType, Optional<Destination>> pair = future.get();
            pair.getSecond().ifPresent(x -> result.put(pair.getFirst(), x));
        }
        return result;
    }

    static class Determinative implements Callable<Pair<CMSType, Optional<Destination>>> {

        private final Processor<CMSType> processor;

        Determinative(Processor<CMSType> processor, Params params) {
            this.processor = processor;
            this.processor.configure(params.getProtocol(), params.getServer());
        }

        @Override
        public Pair<CMSType, Optional<Destination>> call() {
            processor.process();
            return processor.transmit();
        }
    }

}
