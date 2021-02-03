package web.cms.wordpress;

import lombok.SneakyThrows;
import web.struct.AbstractSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class WordPressThemeSource extends AbstractSource {

    @SneakyThrows
    WordPressThemeSource() {
        InputStream in = getClass().getResourceAsStream("/wordpress/themes.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Stream<String> lines = reader.lines();
            lines.map(String::toLowerCase).forEach(sources::add);
            lines.close();
        }
    }

}
