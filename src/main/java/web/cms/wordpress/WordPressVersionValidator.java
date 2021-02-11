package web.cms.wordpress;

import web.struct.Validator;

import java.util.Arrays;

public class WordPressVersionValidator implements Validator {

    private static final String[] leadSymbol = new String[] { "5.", "4.", "3.", "2.", "1.", "0." };

    public boolean validate(String version) {
        return version.length() > 1 && Arrays.asList(leadSymbol).contains(version.substring(0, 2));
    }

}
