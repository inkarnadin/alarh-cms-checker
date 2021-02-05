package web.analyzer.theme;

abstract public class AbstractThemeExtractor implements Extractor<ThemeObject> {

    protected final ThemeObject themeObject = new ThemeObject();

    @Override
    public ThemeObject extract(String responseBody) {
        return themeObject;
    }

}
