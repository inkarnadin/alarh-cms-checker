package web.printer;

import web.struct.ResultContainer;

public class ListPrinter implements Printer {

    @Override
    public void print(ResultContainer resultContainer) {
        for (String result : resultContainer.fetch()) {
            System.out.println(result);
        }
    }

}