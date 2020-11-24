package web.cms.joomla;

import com.google.inject.Inject;
import okhttp3.Response;
import web.http.Host;
import web.http.Request;
import web.http.RequestErrorHandler;
import web.module.annotation.Get;
import web.struct.*;
import web.cms.joomla.annotation.JoomlaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoomlaPluginsProcessor extends AbstractProcessor {

    private final String path = "/administrator/components/";

    private final Request request;
    private final Source source;

    private final Integer[] codes = { 200, 403 };

    @Inject
    JoomlaPluginsProcessor(@Get Request request,
                           @JoomlaPlugin Source source) {
        this.request = request;
        this.source = source;
    }

    @Override
    public void process() {
        List<String> extensions = source.getSources();

        int success = 0;
        int failure = 0;
        int remain = extensions.size();

        List<String> result = new ArrayList<>();
        for (String ext : extensions) {
            Host host = new Host(protocol, server, path + ext);
            try (Response response = request.send(host)) {
                remain--;

                Integer code = response.code();
                if (Arrays.asList(codes).contains(code) && !request.isRedirect(response)) {
                    result.add(ext);
                    success++;
                } else {
                    errorMap.put(response.message(), response.code());
                    failure++;
                }
            }
            System.out.printf("\rRemain: %1s, found: %2s, not found: %3s", remain, success, failure);
        }

        RequestErrorHandler.printError(errorMap);
        ResultStorage.save(null, result);
    }

}
