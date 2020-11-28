package web.cms.datalife;

import lombok.SneakyThrows;
import web.analyzer.LogoMap;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataLifeLogoMap implements LogoMap {

    Map<Long, String> versionMap = new HashMap<>();

    public DataLifeLogoMap() {
        ClassLoader classLoader = this.getClass().getClassLoader();

        put(classLoader.getResource("datalife/4.jpg"), "4.0, 4.3, 4.5, 5.0, 5.1, 5.2, 5.3, 5.5, 5.7, 6.0, 6.2, 6.5, 6.7, 7.0, 7.2, 7.3, 7.5");
        put(classLoader.getResource("datalife/8.2.jpg"), "8.2");
        put(classLoader.getResource("datalife/8.3.jpg"), "8.3");
        put(classLoader.getResource("datalife/9.1.jpg"), "8.5, 9.1, 9.2, 9.4, 9.5, 9.6, 9.7, 9.8, 10.0, 10.1");
        put(classLoader.getResource("datalife/10.2.png"), "10.2, 10.3");
        put(classLoader.getResource("datalife/10.6.png"), "10.4, 10.5, 10.6");
        put(classLoader.getResource("datalife/11.0.png"), "11.0, 11.1, 11.2, 11.3, 12.0, 12.1, 13.0, 13.1, 13.2, 13.3, 14.0, 14.1");
    }

    @SneakyThrows
    private void put(URL resource, String version) {
        if (Objects.nonNull(resource)) {
            File file = new File(resource.toURI());
            byte[] fileContent = Files.readAllBytes(file.toPath());
            versionMap.put((long) fileContent.length, version);
        }
    }

    public String getVersion(long key) {
        return versionMap.getOrDefault(key, "unknown");
    }

}
