package web.http;

import java.util.Map;
import java.util.Objects;

public class RequestErrorHandler {

    public static void printError(Map<String, Integer> map) {
        if (Objects.nonNull(map) && !map.isEmpty()) {
            System.out.println("\nErrors: ");
            map.forEach((key, value) -> System.out.println(value + ": " + key));
        }
    }

}
