package web.cms.wordpress;

import com.google.inject.Inject;
import okhttp3.Response;
import web.struct.*;
import web.cms.wordpress.annotation.WordPressPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordPressPluginProcessor extends AbstractProcessor {

    private final Request request;
    private final Source source;

    private final Integer[] codes = { 200, 403 };

    @Inject
    WordPressPluginProcessor(@WordPressPlugin Request request,
                             @WordPressPlugin Source source) {
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

}