package web.cms;

import web.Connector;
import web.Params;
import web.AbstractChecker;

public class CMSChecker extends AbstractChecker {

    @Override
    public void check(Params params) {
        Connector connector = CMSFactory.getCMSConnector(params.getCmsType());
        connector.configure(params);
        connector.checkPlugins();
    }

}
