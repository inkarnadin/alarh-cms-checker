package web.cms;

import com.google.inject.Inject;

import kotlin.Pair;

import lombok.SneakyThrows;
import web.cms.bitrix.annotation.Bitrix;
import web.cms.datalife.annotation.DataLife;
import web.cms.drupal.annotation.Drupal;
import web.cms.joomla.annotation.Joomla;
import web.cms.maxsite.annotation.MaxSite;
import web.cms.wordpress.annotation.WordPress;
import web.cms.yii.annotation.Yii;
import web.struct.Destination;
import web.struct.Determinant;
import web.struct.Params;
import web.struct.Processor;

import java.util.*;
import java.util.concurrent.*;

public class CMSDeterminant implements Determinant<CMSType, Destination> {

    @Inject @WordPress
    private Processor wpCheckProcessor;
    @Inject @Joomla
    private Processor jmCheckProcessor;
    @Inject @Yii
    private Processor yiiCheckProcessor;
    @Inject @DataLife
    private Processor dleCheckProcessor;
    @Inject @MaxSite
    private Processor mxsCheckProcessor;
    @Inject @Drupal
    private Processor drpCheckProcessor;
    @Inject @Bitrix
    private Processor btxCheckProcessor;

    @Override
    @SneakyThrows
    public Map<CMSType, Destination> define(Params params) {
        Map<CMSType, Destination> result = new HashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        HashSet<Determinative> callables = new HashSet<>();

        callables.add(new Determinative(wpCheckProcessor, params, CMSType.WORDPRESS));
        callables.add(new Determinative(jmCheckProcessor, params, CMSType.JOOMLA));
        callables.add(new Determinative(yiiCheckProcessor, params, CMSType.YII));
        callables.add(new Determinative(dleCheckProcessor, params, CMSType.DATALIFE_ENGINE));
        callables.add(new Determinative(mxsCheckProcessor, params, CMSType.MAXSITE_CMS));
        callables.add(new Determinative(drpCheckProcessor, params, CMSType.DRUPAL));
        callables.add(new Determinative(btxCheckProcessor, params, CMSType.BITRIX));

        List<Future<Pair<CMSType, Optional<Destination>>>> futures = executorService.invokeAll(callables);
        for (Future<Pair<CMSType, Optional<Destination>>> future : futures) {
            Pair<CMSType, Optional<Destination>> pair = future.get();
            pair.getSecond().ifPresent(x -> result.put(pair.getFirst(), x));
        }
        return result;
    }

    static class Determinative implements Callable<Pair<CMSType, Optional<Destination>>> {

        private final Processor processor;
        private final CMSType cmsType;

        Determinative(Processor processor, Params params, CMSType cmsType) {
            this.cmsType = cmsType;
            this.processor = processor;
            this.processor.configure(params.getProtocol(), params.getServer());
        }

        @Override
        public Pair<CMSType, Optional<Destination>> call() {
            processor.process();
            Optional<Destination> transmit = processor.transmit();
            return new Pair<>(cmsType, transmit);
        }
    }

}
