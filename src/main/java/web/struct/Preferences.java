package web.struct;

import lombok.Getter;

public class Preferences {

    @Getter
    private static boolean isActiveMainModule;
    @Getter
    private static boolean isActiveExtendModule;
    @Getter
    private static boolean isLowImportance;

    public static void manageMainModule(boolean value) {
        isActiveMainModule = value;
    }

    public static void manageExtendModule(boolean value) {
        isActiveExtendModule = value;
    }

    public static void manageLowImportanceFilter(boolean value) {
        isLowImportance = value;
    }

}
