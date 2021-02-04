package web.analyzer.theme;

abstract public class AbstractExtractor implements Extractor {

    protected final ThemeObject themeObject = new ThemeObject();

    @Override
    public ThemeObject extract(String responseBody) {
        return themeObject;
    }

}
