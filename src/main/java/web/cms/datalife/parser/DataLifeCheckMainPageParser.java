package web.cms.datalife.parser;

import web.struct.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLifeCheckMainPageParser implements Parser {

    @Override
    public String parse(String text) {
        Pattern pattern = Pattern.compile("<meta name=\"generator\" content=\"(DataLife Engine)\\s\\(http://dle-news.ru\\)\">");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() ? "success" : null;
    }

}
