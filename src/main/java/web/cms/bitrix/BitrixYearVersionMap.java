package web.cms.bitrix;

import web.analyzer.VersionMap;

import java.util.HashMap;
import java.util.Map;

public class BitrixYearVersionMap implements VersionMap {

    Map<Long, String> versionMap = new HashMap<>();

    BitrixYearVersionMap() {
        versionMap.put(2007L, "6.0.0+");
        versionMap.put(2008L, "6.0.0,+");
        versionMap.put(2009L, "8.0.0+");
        versionMap.put(2010L, "9.0.0+");
        versionMap.put(2011L, "10.0.0+");
        versionMap.put(2012L, "11.0.0+");
        versionMap.put(2013L, "12.0.0+");
        versionMap.put(2014L, "14.0.0+");
        versionMap.put(2015L, "14.0.0+");
        versionMap.put(2016L, "15.0.0+");
        versionMap.put(2017L, "15.0.0+");
        versionMap.put(2018L, "17.0.0+");
        versionMap.put(2019L, "18.0.0+");
        versionMap.put(2020L, "20.0.0+");
    }

    @Override
    public String getVersion(long key) {
        return versionMap.getOrDefault(key, "unknown");
    }

}
