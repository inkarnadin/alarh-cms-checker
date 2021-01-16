package web.printer;

import web.struct.Destination;

public class CommonPrinter implements Printer {

    @Override
    public void print(Destination destination) {
        for (String result : destination.fetch())
            System.out.println(result);
    }

}