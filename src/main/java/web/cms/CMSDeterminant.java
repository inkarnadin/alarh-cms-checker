package web.cms;

import com.google.inject.Inject;
import web.cms.yii.annotation.YiiCheck;
import web.struct.Destination;
import web.struct.Determinant;
import web.struct.Params;
import web.struct.Processor;
import web.cms.joomla.annotation.JoomlaCheck;
import web.cms.wordpress.annotation.WordPressCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CMSDeterminant implements Determinant<CMSType> {

    private final Processor wpCheckProcessor;
    private final Processor jmCheckProcessor;
    private final Processor yiiCheckProcessor;

    @Inject
    CMSDeterminant(@WordPressCheck Processor wpCheckProcessor,
                   @JoomlaCheck Processor jmCheckProcessor,
                   @YiiCheck Processor yiiCheckProcessor) {
        this.wpCheckProcessor = wpCheckProcessor;
        this.jmCheckProcessor = jmCheckProcessor;
        this.yiiCheckProcessor = yiiCheckProcessor;
    }

    @Override
    public List<CMSType> define(Params params) {
        List<CMSType> result = new ArrayList<>();

        wpCheckProcessor.configure(params.getProtocol(), params.getServer());
        wpCheckProcessor.process();
        Optional<Destination> wpTransmit = wpCheckProcessor.transmit();
        wpTransmit.ifPresent(destination -> {
            result.add(CMSType.WORDPRESS);
            System.out.println(destination.fetch().get(0));
        });

        jmCheckProcessor.configure(params.getProtocol(), params.getServer());
        jmCheckProcessor.process();
        Optional<Destination> jmTransmit = jmCheckProcessor.transmit();
        jmTransmit.ifPresent(destination -> {
            result.add(CMSType.JOOMLA);
            System.out.println(destination.fetch().get(0));
        });

        yiiCheckProcessor.configure(params.getProtocol(), params.getServer());
        yiiCheckProcessor.process();
        Optional<Destination> yiiTransmit = yiiCheckProcessor.transmit();
        yiiTransmit.ifPresent(destination -> {
            result.add(CMSType.YII);
            System.out.println(destination.fetch().get(0));
        });

        return result;
    }

}
