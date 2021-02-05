package web.analyzer.theme;

public interface Extractor<T> {

    T extract(String responseBody);

}
