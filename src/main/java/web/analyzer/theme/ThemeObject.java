package web.analyzer.theme;

import lombok.Data;

@Data
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

}
