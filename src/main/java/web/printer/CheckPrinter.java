package web.printer;

import web.struct.ResultContainer;

public class CheckPrinter implements Printer {

    @Override
    public void print(ResultContainer resultContainer) {
        System.out.println(resultContainer.fetch().get(0) + " => " + resultContainer.getImportance());
    }

}
