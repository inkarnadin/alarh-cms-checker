package web.cms.yii.parser;

import web.struct.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YiiCheckMainPageParser implements Parser {

    @Override
    public String parse(String text) {
        Pattern pattern = Pattern.compile("<script src=\".*(yii.js).*\"></script>");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() ? "success" : null;
    }

}
