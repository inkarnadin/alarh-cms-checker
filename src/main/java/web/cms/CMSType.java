package web.cms;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CMSType {

    WORDPRESS(0, "wp"),
    JOOMLA(1, "joomla"),
    UNKNOWN(-1, "unknown");

    private final int id;
    private final String name;

    public static CMSType search(String name) {
        for (CMSType cmsType : CMSType.values()) {
            if (name.equalsIgnoreCase(cmsType.name))
                return cmsType;
        }
        return UNKNOWN;
    }

}
