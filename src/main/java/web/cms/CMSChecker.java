package web.cms;

import com.google.inject.Inject;
import web.struct.*;

import java.util.HashMap;
import java.util.Map;

public class CMSChecker extends AbstractChecker {

    private final Determinant<CMSType, Destination> determinant;
    private final Map<CMSType, Destination> types = new HashMap<>();

    @Inject
    CMSChecker(Determinant<CMSType, Destination> determinant) {
        this.determinant = determinant;
    }

    @Override
    public void check(Params params) {
        types.putAll(determinant.define(params));

        types.forEach((cmsType, destination) -> {
            System.out.println(destination.fetch().get(0));

            Connector connector = CMSFactory.getCMSConnector(cmsType);
            connector.configure(params);

            connector.checkVersion();
            //connector.checkPlugins();

            System.out.println();
        });
    }

}
