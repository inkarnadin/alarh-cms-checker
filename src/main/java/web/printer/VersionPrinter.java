package web.printer;

import web.struct.ResultContainer;

public class VersionPrinter implements Printer {

    @Override
    public void print(ResultContainer resultContainer) {
        System.out.printf("  ** Version: %s%n", resultContainer.fetch().get(0));
    }

}
