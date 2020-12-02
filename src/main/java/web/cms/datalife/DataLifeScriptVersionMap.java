package web.cms.datalife;

import web.analyzer.VersionMap;

import java.util.HashMap;
import java.util.Map;

public class DataLifeScriptVersionMap implements VersionMap {

    Map<Long, String> versionMap = new HashMap<>();

    DataLifeScriptVersionMap() {
        versionMap.put(7633L, "8.5");
        versionMap.put(15271L, "9.2");
        versionMap.put(20837L, "9.5");
        versionMap.put(21141L, "9.6");
        versionMap.put(22398L, "9.7");
        versionMap.put(25071L, "9.8");
        versionMap.put(24985L, "10.0");
        versionMap.put(23054L, "10.1");
        versionMap.put(23367L, "10.2");
        versionMap.put(24363L, "10.3");
        versionMap.put(24872L, "10.4");
        versionMap.put(27895L, "10.5");
        versionMap.put(28630L, "10.6, 11.0");
        versionMap.put(28793L, "11.1");
        versionMap.put(29338L, "11.2");
        versionMap.put(30256L, "11.3");
        versionMap.put(30087L, "12.0");
        versionMap.put(30866L, "12.1");
        versionMap.put(32711L, "13.0"); //  для CL
        versionMap.put(32754L, "13.0"); //  для CRLF
        versionMap.put(33248L, "13.1");
        versionMap.put(34024L, "13.2");
        versionMap.put(34562L, "13.3");
        versionMap.put(35206L, "14.0");
        versionMap.put(35516L, "14.1");
    }

    @Override
    public String getVersion(long key) {
        return versionMap.getOrDefault(key, "unknown");
    }

}
