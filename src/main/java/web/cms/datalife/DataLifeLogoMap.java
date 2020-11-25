package web.cms.datalife;

import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataLifeLogoMap {

    Map<Long, String> versionMap = new HashMap<>();

    public DataLifeLogoMap() {
        ClassLoader classLoader = this.getClass().getClassLoader();

        put(classLoader.getResource("datalife/7.jpg"), "7 or lower");
        put(classLoader.getResource("datalife/8.2.jpg"), "8.2");
        put(classLoader.getResource("datalife/9.1.jpg"), "9.1");
        put(classLoader.getResource("datalife/10+.png"), "10 or higher");
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
