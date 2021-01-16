package web.cms;

import com.google.inject.Inject;
import web.struct.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CMSChecker extends AbstractChecker {

    private final Determinant<CMSType, Destination> determinant;

    @Inject
    CMSChecker(Determinant<CMSType, Destination> determinant) {
        this.determinant = determinant;
    }

    @Override
    public void check(Params params) {
        Map<CMSType, Destination> determinate = filterLowImportance(sortByValue(determinant.define(params)));
        determinate.forEach((cmsType, destination) -> {
            System.out.println(destination.fetch().get(0) + " => " + destination.getImportance());

            Connector connector = CMSFactory.getCMSConnector(cmsType);
            connector.configure(params);

            connector.checkVersion();
            //connector.checkPlugins();

            System.out.println();
        });
    }

    private static Map<CMSType, Destination> sortByValue(Map<CMSType, Destination> determinate) {
        List<Map.Entry<CMSType, Destination>> list = new ArrayList<>(determinate.entrySet());
        list.sort(new Comparator<Map.Entry<CMSType, Destination>>() {
            @Override
            public int compare(Map.Entry<CMSType, Destination> o1, Map.Entry<CMSType, Destination> o2) {
                if (o1.getValue().getImportance().ordinal() > o2.getValue().getImportance().ordinal())
                    return 1;
                return 0;
            }
        });

        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map<CMSType, Destination> filterLowImportance(Map<CMSType, Destination> determinate) {
        if (determinate.size() > 1 && !Preferences.isEnableLowImportance()) {
            return determinate.entrySet().stream()
                    .filter(x -> x.getValue().getImportance().ordinal() > 1)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return determinate;
    }

}
