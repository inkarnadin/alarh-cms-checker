package web.cms;

import com.google.inject.Inject;
import web.struct.*;

public class CMSChecker extends AbstractChecker {

    private final Determinant<CMSType, Destination> determinant;

    @Inject
    CMSChecker(Determinant<CMSType, Destination> determinant) {
        this.determinant = determinant;
    }

    @Override
    public void check(Params params) {
        determinant.define(params).forEach((cmsType, destination) -> {
            System.out.println(destination.fetch().get(0) + " => " + destination.getImportance());

            Connector connector = CMSFactory.getCMSConnector(cmsType);
            connector.configure(params);

            connector.checkVersion();
            //connector.checkPlugins();

            System.out.println();
        });
    }

}
