package web.cms.tilda;

import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;

@RequiredArgsConstructor
public class TildaConnector extends AbstractCMSConnector {

    @Override
    public boolean check() {
        return false;
    }

}

