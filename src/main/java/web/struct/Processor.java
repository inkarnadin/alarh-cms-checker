package web.struct;

import java.util.Optional;

public interface Processor {

    void configure(String protocol, String host);
    void process();
    default Optional<Destination> transmit() { return Optional.empty(); }

}
