package web.struct;

import kotlin.Pair;

import java.util.Optional;

public interface Processor<T> {

    void configure(String protocol, String host);

    void process();

    Pair<T, Optional<Destination>> transmit();

}
