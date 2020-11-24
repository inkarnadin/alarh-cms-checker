package web.parser;

import java.util.regex.Matcher;

public class BooleanReturnTextParser extends AbstractTextParser<Boolean> {

    @Override
    public Boolean parse(String txt) {
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();
    }

}
