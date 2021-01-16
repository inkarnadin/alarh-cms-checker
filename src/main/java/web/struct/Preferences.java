package web.struct;

import lombok.Getter;

public class Preferences {

    @Getter
    private static boolean isEnableMainModule = true;
    @Getter
    private static boolean isEnableExtendModule = true;
    @Getter
    private static boolean isEnableLowImportance;
    @Getter
    private static boolean isEnableWhoIsInfo;

    public static void manageMainModule(boolean value) {
        isEnableMainModule = value;
    }

    public static void manageExtendModule(boolean value) {
        isEnableExtendModule = value;
    }

    public static void manageLowImportanceFilter(boolean value) {
        isEnableLowImportance = value;
    }

    public static void manageWhoIsInfo(boolean value) {
        isEnableWhoIsInfo = value;
    }

}
