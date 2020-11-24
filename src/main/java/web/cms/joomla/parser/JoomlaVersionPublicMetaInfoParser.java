package web.cms.joomla.parser;

import web.struct.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoomlaVersionPublicMetaInfoParser implements Parser {

    @Override
    public String parse(String text) {
        Pattern pattern = Pattern.compile("<meta name=\"generator\".*Version\\s(.*)\" />");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() ? matcher.group(1) : "unknown";
    }

}
