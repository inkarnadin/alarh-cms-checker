package web.cms.wordpress.parser;

import web.struct.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordPressVersionPublicMetaInfoParser implements Parser {

    @Override
    public String parse(String text) {
        Pattern pattern = Pattern.compile("<meta name=\"generator\".*WordPress\\s(.*?)\" />");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() ? matcher.group(1) : "unknown";
    }

}
