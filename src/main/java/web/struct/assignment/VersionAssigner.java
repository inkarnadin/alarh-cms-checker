package web.struct.assignment;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.struct.Destination;

import java.util.Collections;
import java.util.Set;

public interface VersionAssigner {

    /**
     * Define and put to destination actual version.
     * If found unrefined version marked as "+" symbol, to look the same version in list without the mark for refine.
     *
     * @param destination - result object
     * @param versionSet - list of versions
     */
    default void assign(Destination destination, Set<ComparableVersion> versionSet) {
        ComparableVersion maxVersion = Collections.max(versionSet);
        ComparableVersion refinedVersion = new ComparableVersion(maxVersion.toString().replace("+", ""));
        if (versionSet.contains(refinedVersion))
            maxVersion = refinedVersion;
        destination.insert(0, maxVersion.toString());
    }

}
