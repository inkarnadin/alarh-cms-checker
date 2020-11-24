package web.db.phpmyadmin;

import web.struct.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhpMyAdminVersionParser implements Parser {

    @Override
    public String parse(String text) {
        Pattern pattern = Pattern.compile("<title>.*phpMyAdmin\\s(.*)\\s.*</title>");
        Matcher matcher = pattern.matcher(text);

        return matcher.find() ? matcher.group(1) : "unknown";
    }

}
