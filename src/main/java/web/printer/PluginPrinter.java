package web.printer;

import web.struct.ResultContainer;

import java.util.List;
import java.util.StringJoiner;

public class PluginPrinter implements Printer {

    @Override
    public void print(ResultContainer resultContainer) {
        List<String> list = resultContainer.fetch();
        StringJoiner stringJoiner = new StringJoiner("\n", "   [\n", "\n   ]");
        for (int i = 1; i < list.size(); i++)
            stringJoiner.add(String.format("    %s", list.get(i)));

        System.out.println(list.get(0));
        System.out.println(stringJoiner);
    }

}
