package web.cms;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.module.*;
import web.struct.Connector;

public class CMSFactory {

    public static Connector getCMSConnector(CMSType cmsType) {
        Injector injector = Guice.createInjector(new CMSModule());
        return injector.getInstance(cmsType.getProvider()).get();
    }

}
