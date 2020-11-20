package web;

public interface Connector {

    void configure(String protocol, String host);

    default boolean check() {
        return true;
    }

    default void checkVersion() {}

    default void checkPlugins() {}
}
