package web.cms.wordpress;

import org.apache.maven.artifact.versioning.ComparableVersion;
import web.analyzer.plugin.AbstractPluginExtractor;
import web.analyzer.plugin.PluginObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordPressPluginExtractor extends AbstractPluginExtractor {

    private final static Pattern namePattern = Pattern.compile("===\\s(.*)\\s===");
    private final static Pattern contributorsPattern = Pattern.compile("Contributors:\\s*(.*)");

    private final static Pattern stableTagPattern = Pattern.compile("Version:\\s*(.*)");
    private final static Pattern versionPattern = Pattern.compile("Stable tag:\\s*(.*)");
    private final static Pattern changeLogPattern = Pattern.compile("== Changelog ==.*?=\\s(.*)\\s");

    @Override
    public PluginObject extract(String responseBody) {
        PluginObject pluginObject = new PluginObject();

        Matcher nameMatcher = namePattern.matcher(responseBody);
        if (nameMatcher.find())
            pluginObject.setName(nameMatcher.group(1));

        Matcher contributorsMatcher = contributorsPattern.matcher(responseBody);
        if (contributorsMatcher.find())
            pluginObject.setContributors(contributorsMatcher.group(1));

        Set<ComparableVersion> versions = new HashSet<>();
        versions.add(new ComparableVersion("<unknown>"));

        Matcher stableTagMatcher = stableTagPattern.matcher(responseBody);
        if (stableTagMatcher.find())
            versions.add(new ComparableVersion(stableTagMatcher.group(1)));

        Matcher versionMatcher = versionPattern.matcher(responseBody);
        if (versionMatcher.find())
            versions.add(new ComparableVersion(versionMatcher.group(1)));

        Matcher changeLogMatcher = changeLogPattern.matcher(responseBody);
        if (changeLogMatcher.find())
            versions.add(new ComparableVersion(changeLogMatcher.group(1)));

        pluginObject.setVersion(Collections.max(versions).toString());
        return pluginObject;
    }

}
