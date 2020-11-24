package web.struct;

public interface Parser {

    default String parse(String text) { return "unknown"; }
}
