package web.cms.joomla;

import com.google.inject.Inject;
import okhttp3.Response;
import web.struct.*;
import web.cms.joomla.annotation.JoomlaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoomlaComponentProcessor extends AbstractProcessor {

    private final Request request;
    private final Source source;

    private final Integer[] codes = { 200, 403 };

    @Inject
    JoomlaComponentProcessor(@JoomlaPlugin Request request,
                             @JoomlaPlugin Source source) {
        this.request = request;
        this.source = source;
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

        List<String> result = new ArrayList<>();
        for (String ext : extensions) {
            try (Response response = request.send(protocol, host, ext)) {
                remain--;

                Integer code = response.code();
                if (Arrays.asList(codes).contains(code)) {
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

    private boolean chechJoomla() {
        Response response = request.send(protocol, host, "com_exactly_not_existing");
        response.close();
        return response.code() == 200;
    }

}
