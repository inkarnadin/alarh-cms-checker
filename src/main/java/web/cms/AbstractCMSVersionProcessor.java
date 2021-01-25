package web.cms;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.struct.assignment.VersionAssigner;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCMSVersionProcessor extends AbstractCMSProcessor implements VersionAssigner {

    protected List<ComparableVersion> versionList = new ArrayList<>();

}
