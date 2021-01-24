package web.struct.assignment;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.env.EnvType;
import web.struct.Destination;

import java.util.Collections;
import java.util.List;

public interface EnvironmentAssigner {

    String successMessage = "  * %s: %s";

    default void assign(Destination destination, EnvType entity, List<ComparableVersion> versionList) {
        destination.insert(0, String.format(successMessage, entity.getName(), Collections.max(versionList).toString()));
    }

}
