package web;

public interface Connector {

    void configure(String protocol, String url);

    default boolean check() {
        return true;
    }

    default void checkVersion() {}

    default void checkPlugins() {}
}
