package web;

import lombok.Getter;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ExtensionStorage {

    @Getter
    private List<String> components = new ArrayList<>();
    @Getter
    private List<String> plugins = new ArrayList<>();

    @SneakyThrows
    public void feedJoomlaComponents() {
        Path path = Paths.get("src/main/resources/joomla-ext.txt");

        Stream<String> lines = Files.lines(path);
        lines.forEach(str -> components.add(str));
        lines.close();
    }

    @SneakyThrows
    public void feedWordPressPlugins() {
        Path path = Paths.get("src/main/resources/wordpress-ext.txt");

        Stream<String> lines = Files.lines(path);
        lines.forEach(str -> plugins.add(str));
        lines.close();
    }

}
