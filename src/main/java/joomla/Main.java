package joomla;

import joomla.processor.CheckComponentProcessor;
import joomla.processor.IProcessor;

public class Main {

    public static void main(String[] args) {
        IProcessor processor = new CheckComponentProcessor(args[0], args[1]);
        processor.process();
    }

}
