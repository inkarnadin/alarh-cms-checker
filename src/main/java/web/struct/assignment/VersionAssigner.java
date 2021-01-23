package web.struct.assignment;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.struct.Destination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface VersionAssigner {

    List<ComparableVersion> versionList = new ArrayList<>();

    default void assign(Destination destination) {
        this.assign(destination, null);
    }

    default void assign(Destination destination, String strategy) {
        destination.insert(0, Collections.max(versionList).toString());
    }

}
