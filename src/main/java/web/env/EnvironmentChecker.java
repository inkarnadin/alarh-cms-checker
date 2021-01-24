package web.env;

import web.struct.AbstractChecker;
import web.struct.Connector;
import web.struct.Params;
import web.struct.Preferences;

public class EnvironmentChecker extends AbstractChecker {

    @Override
    public void check(Params params) {
        Connector phpMyAdminConnector = EnvironmentFactory.getEnvironmentConnector(EnvType.PHP_MY_ADMIN);
        phpMyAdminConnector.configure(params);
        phpMyAdminConnector.check();

        Connector phpConnector = EnvironmentFactory.getEnvironmentConnector(EnvType.PHP);
        phpConnector.configure(params);
        phpConnector.check();

        Connector webServerConnector = EnvironmentFactory.getEnvironmentConnector(EnvType.WEB_SERVER);
        webServerConnector.configure(params);
        webServerConnector.check();

        if (Preferences.isEnableWhoIsInfo()) {
            Connector hosterConnector = EnvironmentFactory.getEnvironmentConnector(EnvType.WHOIS);
            hosterConnector.configure(params);
            hosterConnector.check();
        }
    }

}
