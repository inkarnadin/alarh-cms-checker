package web.analyzer.theme;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import web.env.whois.WhoisObject;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode
public class ThemeObject {

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
