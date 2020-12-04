package web.cms;

import com.google.inject.Inject;

import kotlin.Pair;

import lombok.SneakyThrows;
import web.cms.bitrix.annotation.Bitrix;
import web.cms.datalife.annotation.DataLife;
import web.cms.drupal.annotation.Drupal;
import web.cms.insales.annotation.InSales;
import web.cms.joomla.annotation.Joomla;
import web.cms.lavarel.annotation.Lavarel;
import web.cms.magento.annotation.Magento;
import web.cms.maxsite.annotation.MaxSite;
import web.cms.modx.annotation.ModX;
import web.cms.nuxt.annotation.Nuxt;
import web.cms.opencart.annotation.OpenCart;
import web.cms.rails.annotation.RubyOnRails;
import web.cms.tilda.annotation.Tilda;
import web.cms.vamshop.annotation.VamShop;
import web.cms.vigbo.annotation.Vigbo;
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
    @Inject @ModX
    private Processor mdxCheckProcessor;
    @Inject @Lavarel
    private Processor lvrCheckProcessor;
    @Inject @Tilda
    private Processor tldCheckProcessor;
    @Inject @VamShop
    private Processor vmsCheckProcessor;
    @Inject @Nuxt
    private Processor nxtCheckProcessor;
    @Inject @Magento
    private Processor mgnCheckProcessor;
    @Inject @OpenCart
    private Processor ocsCheckProcessor;
    @Inject @InSales
    private Processor insCheckProcessor;
    @Inject @Vigbo
    private Processor vgbCheckProcessor;
    @Inject @RubyOnRails
    private Processor rorCheckProcessor;

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
        callables.add(new Determinative(mdxCheckProcessor, params, CMSType.MODX));
        callables.add(new Determinative(lvrCheckProcessor, params, CMSType.LAVAREL));
        callables.add(new Determinative(tldCheckProcessor, params, CMSType.TILDA));
        callables.add(new Determinative(vmsCheckProcessor, params, CMSType.VAM_SHOP));
        callables.add(new Determinative(nxtCheckProcessor, params, CMSType.NUXT_JS));
        callables.add(new Determinative(mgnCheckProcessor, params, CMSType.MAGENTO));
        callables.add(new Determinative(ocsCheckProcessor, params, CMSType.OPENCART));
        callables.add(new Determinative(insCheckProcessor, params, CMSType.INSALES));
        callables.add(new Determinative(vgbCheckProcessor, params, CMSType.VIGBO));
        callables.add(new Determinative(rorCheckProcessor, params, CMSType.RUBY_ON_RAILS));

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
