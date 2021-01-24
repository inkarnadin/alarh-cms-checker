package web.struct.assignment;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.struct.Destination;

import java.util.Collections;
import java.util.List;

public interface VersionAssigner {

    default void assign(Destination destination, List<ComparableVersion> versionList) {
        this.assign(destination, versionList, null);
    }

    default void assign(Destination destination, List<ComparableVersion> versionList, String strategy) {
        destination.insert(0, Collections.max(versionList).toString());
    }

}
