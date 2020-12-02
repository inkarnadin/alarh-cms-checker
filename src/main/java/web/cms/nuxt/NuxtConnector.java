package web.cms.nuxt;

import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;

@RequiredArgsConstructor
public class NuxtConnector extends AbstractCMSConnector {

    @Override
    public boolean check() {
        return false;
    }

}

