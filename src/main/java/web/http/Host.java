package web.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class Host {

    private final String protocol;
    private final String server;
    private final String path;

    public String createUrl() {
        StringBuilder stringBuilder = new StringBuilder(protocol)
                .append("://")
                .append(server)
                .append("/")
                .append(Objects.nonNull(path) ? path : "");
        return stringBuilder.toString();
    }

}
