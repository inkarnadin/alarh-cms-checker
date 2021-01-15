package web.analyzer;

import lombok.Getter;

import java.util.regex.Pattern;

public class DublinCoreSignStorage implements SignStorage {

    @Getter
    private static final Pattern[] elements = new Pattern[] {
            Pattern.compile("DC\\.Title"),
            Pattern.compile("DC\\.Creator"),
            Pattern.compile("DC\\.Subject"),
            Pattern.compile("DC\\.Description"),
            Pattern.compile("DC\\.Contributor"),
            Pattern.compile("DC\\.Date"),
            Pattern.compile("DC\\.Type"),
            Pattern.compile("DC\\.Format"),
            Pattern.compile("DC\\.Identifier"),
            Pattern.compile("DC\\.Source"),
            Pattern.compile("DC\\.Language"),
            Pattern.compile("DC\\.Relation"),
            Pattern.compile("DC\\.Coverage"),
            Pattern.compile("DC\\.Rights")
    };

}
