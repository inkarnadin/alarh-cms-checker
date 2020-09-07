package joomla;

import joomla.processor.CheckComponentProcessor;
import joomla.processor.IProcessor;

public class Main {

    public static void main(String[] args) {
        IProcessor processor = new CheckComponentProcessor("http", "youmagic.com");
        processor.process();
    }

}
