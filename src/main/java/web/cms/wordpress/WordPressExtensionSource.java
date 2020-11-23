package web.cms.wordpress;

import lombok.SneakyThrows;
import web.struct.AbstractSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class WordPressExtensionSource extends AbstractSource {

    @SneakyThrows
    WordPressExtensionSource() {
        InputStream in = getClass().getResourceAsStream("/wordpress-ext.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Stream<String> lines = reader.lines();
            lines.forEach(sources::add);
            lines.close();
        }
    }

}
