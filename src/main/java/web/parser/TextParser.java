package web.parser;

import java.util.regex.Pattern;

public interface TextParser<T> {

   void configure(Pattern pattern, Integer groupNo);
   T parse(String txt);

}
