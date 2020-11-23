package web.struct;

public interface Connector {

    void configure(Params params);

    default boolean check() {
        return true;
    }

    default void checkVersion() {}

    default void checkPlugins() {}
}
