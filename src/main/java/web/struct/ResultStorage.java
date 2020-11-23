package web.struct;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResultStorage {

    @SneakyThrows
    public static void save(String path, List<String> result) {
        if (Objects.isNull(path)) {
            path = "result.txt";
        }

        FileWriter file = new FileWriter(path);
        result.forEach(x -> {
            try {
                file.append(x).append("\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        file.close();
        System.out.println("\n");
        System.out.println("Result path: " + new File(path).getAbsolutePath());
    }

    public static void save(String result) {
        save(null, Collections.singletonList(result));
    }

}
