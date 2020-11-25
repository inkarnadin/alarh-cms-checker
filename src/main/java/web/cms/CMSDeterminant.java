package web.cms;

import com.google.inject.Inject;
import web.cms.datalife.annotation.DataLife;
import web.cms.joomla.annotation.Joomla;
import web.cms.wordpress.annotation.WordPress;
import web.cms.yii.annotation.Yii;
import web.struct.Destination;
import web.struct.Determinant;
import web.struct.Params;
import web.struct.Processor;

import java.util.*;

public class CMSDeterminant implements Determinant<CMSType, Destination> {

    private final Processor wpCheckProcessor;
    private final Processor jmCheckProcessor;
    private final Processor yiiCheckProcessor;
    private final Processor dleCheckProcessor;

    @Inject
    CMSDeterminant(@WordPress Processor wpCheckProcessor,
                   @Joomla Processor jmCheckProcessor,
                   @Yii Processor yiiCheckProcessor,
                   @DataLife Processor dleCheckProcessor) {
        this.wpCheckProcessor = wpCheckProcessor;
        this.jmCheckProcessor = jmCheckProcessor;
        this.yiiCheckProcessor = yiiCheckProcessor;
        this.dleCheckProcessor = dleCheckProcessor;
    }

    @Override
    public Map<CMSType, Destination> define(Params params) {
        Map<CMSType, Destination> result = new HashMap<>();

        wpCheckProcessor.configure(params.getProtocol(), params.getServer());
        wpCheckProcessor.process();
        Optional<Destination> wpTransmit = wpCheckProcessor.transmit();
        wpTransmit.ifPresent(destination -> result.put(CMSType.WORDPRESS, destination));

        jmCheckProcessor.configure(params.getProtocol(), params.getServer());
        jmCheckProcessor.process();
        Optional<Destination> jmTransmit = jmCheckProcessor.transmit();
        jmTransmit.ifPresent(destination -> result.put(CMSType.JOOMLA, destination));

        yiiCheckProcessor.configure(params.getProtocol(), params.getServer());
        yiiCheckProcessor.process();
        Optional<Destination> yiiTransmit = yiiCheckProcessor.transmit();
        yiiTransmit.ifPresent(destination -> result.put(CMSType.YII, destination));

        dleCheckProcessor.configure(params.getProtocol(), params.getServer());
        dleCheckProcessor.process();
        Optional<Destination> dleTransmit = dleCheckProcessor.transmit();
        dleTransmit.ifPresent(destination -> result.put(CMSType.DATALIFE_ENGINE, destination));

        return result;
    }

}
