package web.struct;

import kotlin.Pair;
import web.cms.CMSType;

import java.util.Optional;

public interface Connector {

    void configure(Params params);

    default Pair<CMSType, Optional<Destination>> check() {
        return null;
    }

    default void checkVersion() {}

    default void checkPlugins() {}

}
