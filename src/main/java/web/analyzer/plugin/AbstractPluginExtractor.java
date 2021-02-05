package web.analyzer.plugin;

import web.analyzer.theme.Extractor;

public class AbstractPluginExtractor implements Extractor<PluginObject> {

    @Override
    public PluginObject extract(String responseBody) {
        return new PluginObject();
    }

}
