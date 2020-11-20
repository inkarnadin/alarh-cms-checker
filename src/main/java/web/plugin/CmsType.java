package web.plugin;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CmsType {

    WORDPRESS(0, "wp"),
    JOOMLA(1, "joomla"),
    UNKNOWN(-1, "unknown");

    private final int id;
    private final String name;

    public static CmsType search(String name) {
        for (CmsType cmsType : CmsType.values()) {
            if (name.equalsIgnoreCase(cmsType.name))
                return cmsType;
        }
        return UNKNOWN;
    }

}
