package web.analyzer.theme;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode
public class ThemeObject {

    private String path;
    private String themeName;
    private String themeUri;
    private String author;
    private String authorUri;
    private String description;
    private String version;
    private String license;
    private String licenseUri;
    private String textDomain;
    private String tags;
    private Boolean isCustom = true;

    public String getShortInfo() {
        String custom = isCustom ? " (Custom)" : " (Popular)";
        if (Objects.nonNull(themeName) && Objects.nonNull(version))
            return themeName + " v" + version + custom;
        else if (Objects.nonNull(themeName))
            return themeName + custom;
        else
            return "<unknown>";
    }

    @SneakyThrows
    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("\n", "   [\n", "\n   ]");
        Field[] fields = ThemeObject.class.getDeclaredFields();

        for (Field field : fields) {
            Object value = field.get(this);
            if (Objects.nonNull(value) && !"".equals(value.toString()))
                stringJoiner.add(String.format("    %s = %s", field.getName(), value.toString()));
        }
        return stringJoiner.toString();
    }

}
