package web.cms;

import com.google.inject.Inject;
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

    @Inject
    CMSDeterminant(@WordPressCheck Processor wpCheckProcessor,
                   @JoomlaCheck Processor jmCheckProcessor) {
        this.wpCheckProcessor = wpCheckProcessor;
        this.jmCheckProcessor = jmCheckProcessor;
    }

    @Override
    public List<CMSType> define(Params params) {
        List<CMSType> result = new ArrayList<>();

        wpCheckProcessor.configure(params.getProtocol(), params.getServer());
        wpCheckProcessor.process();
        Optional<Destination> transmit = wpCheckProcessor.transmit();
        transmit.ifPresent(destination -> {
            result.add(CMSType.WORDPRESS);
            System.out.println(destination.fetch().get(0));
        });

        jmCheckProcessor.configure(params.getProtocol(), params.getServer());
        jmCheckProcessor.process();
        Optional<Destination> transmit_ = jmCheckProcessor.transmit();
        transmit_.ifPresent(destination -> {
            result.add(CMSType.JOOMLA);
            System.out.println(destination.fetch().get(0));
        });

        return result;
    }

}
