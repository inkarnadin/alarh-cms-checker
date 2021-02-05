package web.cms.wordpress;

import web.analyzer.theme.AbstractThemeExtractor;
import web.analyzer.theme.ThemeObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordPressThemeExtractor extends AbstractThemeExtractor {

    private final static Pattern namePattern = Pattern.compile("Theme Name:\\s*(.*)");
    private final static Pattern descPattern = Pattern.compile("Description:\\s*(.*)");
    private final static Pattern versionPattern = Pattern.compile("Version:\\s*(.*)");
    private final static Pattern authorPattern = Pattern.compile("Author:\\s*(.*)");

    @Override
    public ThemeObject extract(String responseBody) {
        Matcher themeNameMatcher = namePattern.matcher(responseBody);
        if (themeNameMatcher.find())
            themeObject.setThemeName(themeNameMatcher.group(1));

        Matcher themeDescriptionMatcher = descPattern.matcher(responseBody);
        if (themeDescriptionMatcher.find())
            themeObject.setDescription(themeDescriptionMatcher.group(1));

        Matcher themeVersionMatcher = versionPattern.matcher(responseBody);
        if (themeVersionMatcher.find())
            themeObject.setVersion(themeVersionMatcher.group(1));

        Matcher themeAuthorMatcher = authorPattern.matcher(responseBody);
        if (themeAuthorMatcher.find())
            themeObject.setAuthor(themeAuthorMatcher.group(1));

        return themeObject;
    }

}
