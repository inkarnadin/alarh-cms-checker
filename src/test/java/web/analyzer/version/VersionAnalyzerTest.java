package web.analyzer.version;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionAnalyzerTest {

    @Test
    public void compareVersion() {
        String txt = "@since 4.5.0 \\n{} @since 1.1.0 ___.*\\n @since 9.0.0 ";
        Pattern pattern = Pattern.compile("@since\\s(.*?)\\s");
        Matcher matcher = pattern.matcher(txt);

        List<ComparableVersion> allMatches = new ArrayList<>();
        allMatches.add(new ComparableVersion("unknown"));
        while (matcher.find()) {
            allMatches.add(new ComparableVersion(matcher.group(1)));
        }
        ComparableVersion max = Collections.max(allMatches);
        Assert.assertEquals("9.0.0", max.toString());
    }

}