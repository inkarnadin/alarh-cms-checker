package web.db;

import web.Connector;

public class DBAdminChecker {

    public static void check() {
        Connector connector = DBAdminFactory.getDBAdmin();
        connector.configure("http", "ban-dvor.ru");
        connector.checkVersion();
    }

}
