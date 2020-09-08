package web.wordpress.processor;

import okhttp3.Response;
import web.IProcessor;
import web.IRequest;
import web.ExtensionStorage;
import web.ResultStorage;
import web.wordpress.request.WordPressCheckPluginRequest;

import java.util.ArrayList;
import java.util.List;

public class WordPressCheckPluginProcessor implements IProcessor {

    private IRequest request = new WordPressCheckPluginRequest();
    private ExtensionStorage storage = new ExtensionStorage();

    private final String protocol;
    private final String url;

    public WordPressCheckPluginProcessor(String protocol, String url) {
        this.protocol = protocol;
        this.url = url;

        storage.feedWordPressPlugins();
    }

    @Override
    public void process() {
        List<String> extensions = storage.getPlugins();

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
            System.out.print(String.format("\rRemain: %1s, found: %2s, not found: %3s, exception: %4s", remain, success, failure, error));
        }
        ResultStorage.save(null, result);
    }

}
