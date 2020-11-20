package web;

public interface Processor {

    void configure(String protocol, String url);
    void process();

}
