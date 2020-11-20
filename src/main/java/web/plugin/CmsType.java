package web.plugin;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CmsType {

    WORDPRESS("wp"),
    JOOMLA("joomla"),
    UNKNOWN("unknown");

    private final String name;

    public static CmsType search(String name) {
        for (CmsType cmsType : CmsType.values()) {
            if (name.equalsIgnoreCase(cmsType.name))
                return cmsType;
        }
        return UNKNOWN;
    }

}
