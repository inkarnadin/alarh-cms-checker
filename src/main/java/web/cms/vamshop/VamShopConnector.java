package web.cms.vamshop;

import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;

@RequiredArgsConstructor
public class VamShopConnector extends AbstractCMSConnector {

    @Override
    public boolean check() {
        return false;
    }

}

