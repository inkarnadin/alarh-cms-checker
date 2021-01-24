package web.printer;

import web.struct.Destination;

public interface Printer {

    default void print(Destination destination) {}
    default void print(Destination destination, String entity) {}

}
