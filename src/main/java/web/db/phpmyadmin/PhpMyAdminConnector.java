package web.db.phpmyadmin;

import lombok.RequiredArgsConstructor;
import web.struct.Processor;
import web.db.AbstractDBConnector;

@RequiredArgsConstructor
public class PhpMyAdminConnector extends AbstractDBConnector {

    private final Processor processor;

    @Override
    public void checkVersion() {
        processor.configure(params.getProtocol(), params.getHost());
        processor.process();
    }
}
