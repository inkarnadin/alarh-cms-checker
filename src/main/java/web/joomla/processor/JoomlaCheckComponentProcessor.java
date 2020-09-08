package web.joomla.processor;

import web.IProcessor;
import web.IRequest;
import web.JoomlaComponentStorage;
import web.ResultStorage;
import web.joomla.request.JoomlaCheckComponentRequest;
import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;

public class JoomlaCheckComponentProcessor implements IProcessor {

    private IRequest request = new JoomlaCheckComponentRequest();
    private JoomlaComponentStorage storage = new JoomlaComponentStorage();

    private final String protocol;
    private final String url;

    public JoomlaCheckComponentProcessor(String protocol, String url) {
        this.protocol = protocol;
        this.url = url;

        storage.feedComponents();
    }

    @Override
    public void process() {
        List<String> components = storage.getComponents();

        if (chechJoomla()) {
            System.out.println("Not the Joomla-build site!");
            return;
        }

        int success = 0;
        int failure = 0;
        int remain = storage.getCount();
        int error = 0;

        List<String> result = new ArrayList<>();
        for (String component : components) {
            try {
                remain--;
                Response response = request.send(protocol, url, component);
                if (response.code() == 200) {
                    result.add(component);
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

    private boolean chechJoomla() {
        Response response = request.send(protocol, url, "com_exactly_not_existing");
        response.close();
        return response.code() == 200;
    }

}
