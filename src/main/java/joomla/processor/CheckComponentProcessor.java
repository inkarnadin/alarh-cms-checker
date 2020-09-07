package joomla.processor;

import joomla.ComponentStorage;
import joomla.request.CheckComponentRequest;
import joomla.request.IRequest;
import okhttp3.Response;

import java.util.List;

public class CheckComponentProcessor implements IProcessor {

    private IRequest request = new CheckComponentRequest();
    private ComponentStorage storage = new ComponentStorage();

    private final String protocol;
    private final String url;

    public CheckComponentProcessor(String protocol, String url) {
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

        int i = 0;
        for (String component : components) {
            Response response = request.send(protocol, url, component);
            if (response.code() == 200) {
                System.out.println(String.format("FIND: %s", component));
                i++;
            }
        }
        System.out.println(String.format("\nFind %1s of %2s", i, storage.getCount()));
    }

    private boolean chechJoomla() {
        Response response = request.send(protocol, url, "not_exists_com");
        return response.code() == 200;
    }

}
