package web.analyzer.plugin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.util.Objects;

@Data
@EqualsAndHashCode
public class PluginObject {

    private String path;
    private String name;
    private String contributors;
    private String tags;
    private String version;

    public boolean isFilled() {
        return Objects.nonNull(name);
    }

    @SneakyThrows
    @Override
    public String toString() {
        if (Objects.nonNull(name) && Objects.nonNull(version))
            return name + ": " + version;
        else if (Objects.nonNull(name))
            return name + ": <unknown>";
        else
            return "";
    }

}
