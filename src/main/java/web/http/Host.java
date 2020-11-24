package web.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class Host {

    @Setter
    private boolean isBegetProtection;

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
