package web.env;

import web.struct.AbstractChecker;
import web.struct.Connector;
import web.struct.Params;

public class EnvironmentChecker extends AbstractChecker {

    @Override
    public void check(Params params) {
        Connector phpMyAdminConnector = EnvironmentFactory.getEnvironmentConnector(EnvType.PHP_MY_ADMIN);
        phpMyAdminConnector.configure(params);
        phpMyAdminConnector.checkVersion();

        System.out.println();

        Connector phpConnector = EnvironmentFactory.getEnvironmentConnector(EnvType.PHP);
        phpConnector.configure(params);
        phpConnector.checkVersion();
    }

}
