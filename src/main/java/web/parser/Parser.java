package web.parser;

public interface Parser {

    default String parse(String text) { return "unknown"; }
}
