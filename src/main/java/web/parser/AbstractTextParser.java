package web.parser;

import java.util.Objects;
import java.util.regex.Pattern;

public abstract class AbstractTextParser<T> implements TextParser<T> {

    protected Pattern pattern;
    protected Integer groupNo;

    @Override
    public void configure(Pattern pattern, Integer groupNo) {
        this.pattern = pattern;
        this.groupNo = (Objects.nonNull(groupNo)) ? groupNo : 0;
    }

    @Override
    public T parse(String txt) {
        return null;
    }

}
