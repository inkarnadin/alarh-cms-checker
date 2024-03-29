package web.cms.classic.joomla;

import lombok.SneakyThrows;
import web.struct.AbstractSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class JoomlaExtensionSource extends AbstractSource {

    @SneakyThrows
    JoomlaExtensionSource() {
        InputStream in = getClass().getResourceAsStream("/joomla-extensions-core.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Stream<String> lines = reader.lines();
            lines.forEach(sources::add);
            lines.close();
        }
    }

}
