package web;

import web.joomla.processor.JoomlaCheckComponentProcessor;

public class Main {

    public static void main(String[] args) {
        IProcessor processor = new JoomlaCheckComponentProcessor(args[0], args[1]);
        processor.process();
    }

}
