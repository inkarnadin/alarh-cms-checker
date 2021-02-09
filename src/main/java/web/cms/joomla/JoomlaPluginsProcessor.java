package web.cms.joomla;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.cms.AbstractCMSProcessor;

import web.http.Request;

import static web.http.RequestMarker.HEAD;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class JoomlaPluginsProcessor extends AbstractCMSProcessor {

    private final Request request;
    @Named(HEAD)
    private final Request headRequest;

    @Override
    public void process() {

    }

}
