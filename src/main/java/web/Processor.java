package web;

public interface Processor {

    void configure(String protocol, String host);
    void process();

}
