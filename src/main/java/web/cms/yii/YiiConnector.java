package web.cms.yii;

import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSConnector;

@RequiredArgsConstructor
public class YiiConnector extends AbstractCMSConnector {

    @Override
    public boolean check() {
        return false;
    }

}

