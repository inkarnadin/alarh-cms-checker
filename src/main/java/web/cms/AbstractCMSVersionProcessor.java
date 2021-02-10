package web.cms;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.struct.assignment.VersionAssigner;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCMSVersionProcessor extends AbstractCMSProcessor implements VersionAssigner {

    protected Set<ComparableVersion> versionSet = new HashSet<>();

}
