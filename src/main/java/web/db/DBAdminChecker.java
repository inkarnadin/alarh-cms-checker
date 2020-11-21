package web.db;

import web.AbstractChecker;
import web.Connector;
import web.Params;

public class DBAdminChecker extends AbstractChecker {

    @Override
    public void check(Params params) {
        Connector connector = DBAdminFactory.getDBAdmin();
        connector.configure(params);
        connector.checkVersion();
    }

}
