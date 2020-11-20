package web.cms.joomla;

import com.google.inject.Inject;
import web.*;
import okhttp3.Response;
import web.cms.joomla.annotation.JoomlaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JoomlaCheckComponentProcessor implements PluginProcessor {

    private final Request request;
    private final Source source;

    private String protocol;
    private String url;

    @Inject
    JoomlaCheckComponentProcessor(@JoomlaPlugin Request request,
                                  @JoomlaPlugin Source source) {
        this.request = request;
        this.source = source;
    }

    @Override
    public void configure(String protocol, String url) {
        Objects.requireNonNull(protocol, "Empty protocol value!");
        Objects.requireNonNull(url, "Empty url value!");

        this.protocol = protocol;
        this.url = url;
    }

    @Override
    public void process() {
        List<String> extensions = source.getSources();

        if (chechJoomla()) {
            System.out.println("Not the Joomla-build site!");
            return;
        }

        int success = 0;
        int failure = 0;
        int remain = extensions.size();
        int error = 0;

        List<String> result = new ArrayList<>();
        for (String ext : extensions) {
            try {
                remain--;
                Response response = request.send(protocol, url, ext);
                if (response.code() == 200) {
                    result.add(ext);
                    success++;
                } else {
                    failure++;
                }
                response.close();
            } catch (Exception e) {
                error++;
            }
            System.out.printf("\rRemain: %1s, found: %2s, not found: %3s, exception: %4s", remain, success, failure, error);
        }
        ResultStorage.save(null, result);
    }

    private boolean chechJoomla() {
        Response response = request.send(protocol, url, "com_exactly_not_existing");
        response.close();
        return response.code() == 200;
    }

}
