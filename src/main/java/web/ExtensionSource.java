package web;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ExtensionSource {

    @Getter
    private final List<String> components = new ArrayList<>();
    @Getter
    private final List<String> plugins = new ArrayList<>();

    @SneakyThrows
    public void feedJoomlaComponents() {
        InputStream in = getClass().getResourceAsStream("/joomla-ext.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Stream<String> lines = reader.lines();
            lines.forEach(plugins::add);
            lines.close();
        }
    }

    @SneakyThrows
    public void feedWordPressPlugins() {
        InputStream in = getClass().getResourceAsStream("/wordpress-ext.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            Stream<String> lines = reader.lines();
            lines.forEach(plugins::add);
            lines.close();
        }
    }

}
