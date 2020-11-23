package web.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
                .append(path);
        return stringBuilder.toString();
    }

}
