package web.struct.assignment;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.env.EnvType;
import web.struct.ResultContainer;

import java.util.Collections;
import java.util.Set;

public interface EnvironmentAssigner {

    String successMessage = "  * %s: %s";

    default void assign(ResultContainer resultContainer, EnvType entity, Set<ComparableVersion> versionSet) {
        resultContainer.insert(0, String.format(successMessage, entity.getName(), Collections.max(versionSet).toString()));
    }

}
