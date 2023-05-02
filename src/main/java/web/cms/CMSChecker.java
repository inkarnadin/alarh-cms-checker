package web.cms;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import web.printer.Printer;
import web.struct.*;
import web.struct.ResultContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class CMSChecker extends AbstractChecker {

    private final Determinant<CMSType, ResultContainer> determinant;
    @Named("checkPrinter")
    private final Printer printer;

    @Override
    public void check(Params params) {
        Map<CMSType, ResultContainer> determinate = filterLowImportance(sortByValue(determinant.define(params)));
        determinate.forEach((cmsType, destination) -> {
            printer.print(destination);

            Connector connector = CMSFactory.getCMSConnector(cmsType);
            connector.configure(params);

            connector.checkVersion();
            connector.checkData();
            connector.checkTheme();
            connector.checkPlugins();

            System.out.println();
        });
    }

    private static Map<CMSType, ResultContainer> sortByValue(Map<CMSType, ResultContainer> determinate) {
        List<Map.Entry<CMSType, ResultContainer>> list = new ArrayList<>(determinate.entrySet());
        list.sort(new Comparator<Map.Entry<CMSType, ResultContainer>>() {
            @Override
            public int compare(Map.Entry<CMSType, ResultContainer> o1, Map.Entry<CMSType, ResultContainer> o2) {
                if (o1.getValue().getImportance().ordinal() > o2.getValue().getImportance().ordinal()) {
                    return 1;
                }
                return 0;
            }
        });

        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<CMSType, ResultContainer> filterLowImportance(Map<CMSType, ResultContainer> determinate) {
        Map<CMSType, ResultContainer> result = determinate;
        if (determinate.size() > 1 && !Preferences.isEnableLowImportance()) {
            result = determinate.entrySet().stream()
                    .filter(x -> x.getValue().getImportance().ordinal() > 1)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        Context.update(determinate.keySet().toArray(new CMSType[0]));
        return result;
    }

}
