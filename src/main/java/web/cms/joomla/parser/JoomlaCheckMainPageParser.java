package web.cms.joomla.parser;

import web.struct.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoomlaCheckMainPageParser implements Parser {

    @Override
    public String parse(String text) {
        Pattern pattern = Pattern.compile("<meta name=\"generator\" content=\"(Joomla!).*/>");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() ? "success" : null;
    }
}
