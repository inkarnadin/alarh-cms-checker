package web.cms.wordpress;

import com.google.inject.Inject;
import okhttp3.Response;
import web.*;
import web.cms.wordpress.annotation.WordPressPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordPressCheckPluginProcessor implements PluginProcessor {

    private final Request request;
    private final Source source;

    private String protocol;
    private String url;

    @Inject
    WordPressCheckPluginProcessor(@WordPressPlugin Request request,
                                  @WordPressPlugin Source source) {
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

        int success = 0;
        int failure = 0;
        int remain = extensions.size();
        int error = 0;

        List<String> result = new ArrayList<>();
        for (String ext : extensions) {
            try {
                remain--;
                Response response = request.send(protocol, url, ext);
                if (response.code() != 404) {
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

}
