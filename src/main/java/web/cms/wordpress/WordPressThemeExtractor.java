package web.cms.wordpress;

import web.analyzer.theme.AbstractExtractor;
import web.analyzer.theme.ThemeObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordPressThemeExtractor extends AbstractExtractor {

    @Override
    public ThemeObject extract(String responseBody) {
        Matcher themeNameMatcher = Pattern.compile("Theme Name:\\s*(.*)").matcher(responseBody);
        if (themeNameMatcher.find())
            themeObject.setThemeName(themeNameMatcher.group(1));

        Matcher themeDescriptionMatcher = Pattern.compile("Description:\\s*(.*)").matcher(responseBody);
        if (themeDescriptionMatcher.find())
            themeObject.setDescription(themeDescriptionMatcher.group(1));

        Matcher themeVersionMatcher = Pattern.compile("Version:\\s*(.*)").matcher(responseBody);
        if (themeVersionMatcher.find())
            themeObject.setVersion(themeVersionMatcher.group(1));

        Matcher themeAuthorMatcher = Pattern.compile("Author:\\s*(.*)").matcher(responseBody);
        if (themeAuthorMatcher.find())
            themeObject.setAuthor(themeAuthorMatcher.group(1));

        return themeObject;
    }

}
