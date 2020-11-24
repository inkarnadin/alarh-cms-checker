package web.cms.datalife;

import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;

@RequiredArgsConstructor
public class DataLifeConnector extends AbstractCMSConnector {

    @Override
    public boolean check() {
        return false;
    }

}

