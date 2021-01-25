package web.struct.assignment;

import kotlin.Pair;
import web.analyzer.Importance;
import web.cms.CMSType;
import web.struct.Destination;

import java.util.List;

import static web.analyzer.Importance.UNDEFINED;

public interface DefaultAssigner {

    String successMessage = "  * %s tags have been found (%s/%s)";

    default void assign(Destination destination, List<Pair<Boolean, Importance>> result, CMSType cmsType) {
        long count = result.stream().filter(Pair::getFirst).count();
        Importance max = result.stream()
                .filter(Pair::getFirst)
                .map(Pair::getSecond)
                .max(Importance::compareTo).orElse(UNDEFINED);

        if (count > 0) {
            destination.setImportance(max);
            destination.insert(0, String.format(
                    successMessage,
                    cmsType.getName(),
                    count,
                    result.size())
            );
        }
    }

}
