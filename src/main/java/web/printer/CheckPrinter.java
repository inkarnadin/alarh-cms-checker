package web.printer;

import web.struct.Destination;

public class CheckPrinter implements Printer {

    @Override
    public void print(Destination destination) {
        System.out.println(destination.fetch().get(0) + " => " + destination.getImportance());
    }

}
