package web.env;

import web.struct.AbstractChecker;
import web.struct.Connector;
import web.struct.Params;

public class EnvironmentChecker extends AbstractChecker {

    @Override
    public void check(Params params) {
        Connector connector = EnvironmentFactory.getDBAdmin(EnvType.PHP_MY_ADMIN);
        connector.configure(params);
        connector.checkVersion();
    }

}
