package web.struct.assignment;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.struct.Destination;

import java.util.Collections;
import java.util.List;

public interface VersionAssigner {

    default void assign(Destination destination, List<ComparableVersion> versionList) {
        destination.insert(0, Collections.max(versionList).toString());
    }

}
