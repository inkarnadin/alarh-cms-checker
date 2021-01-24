package web.parser;

import java.util.regex.Matcher;

public class StringReturnTextParser extends AbstractTextParser<String> {

    @Override
    public String parse(String txt) {
        Matcher matcher = pattern.matcher(txt);
        return matcher.find() ? matcher.group(groupNo) : "<unknown>";
    }

}
