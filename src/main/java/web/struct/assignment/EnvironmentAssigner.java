package web.struct.assignment;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.env.EnvType;
import web.struct.Destination;

import java.util.Collections;
import java.util.Set;

public interface EnvironmentAssigner {

    String successMessage = "  * %s: %s";

    default void assign(Destination destination, EnvType entity, Set<ComparableVersion> versionSet) {
        destination.insert(0, String.format(successMessage, entity.getName(), Collections.max(versionSet).toString()));
    }

}
