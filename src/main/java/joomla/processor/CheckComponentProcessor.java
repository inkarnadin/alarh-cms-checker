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

        for (String component : components) {
            Response response = request.send(protocol, url, component);
            if (response.code() == 200)
                System.out.println(String.format("FIND: %s", component));
        }
    }

}
