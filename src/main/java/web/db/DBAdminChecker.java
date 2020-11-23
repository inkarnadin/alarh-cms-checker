package web.db;

import web.struct.AbstractChecker;
import web.struct.Connector;
import web.struct.Params;

public class DBAdminChecker extends AbstractChecker {

    @Override
    public void check(Params params) {
        Connector connector = DBAdminFactory.getDBAdmin();
        connector.configure(params);
        connector.checkVersion();
    }

}
