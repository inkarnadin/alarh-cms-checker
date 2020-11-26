package web.http;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class Host {

    private final String protocol;
    private final String server;

    @Setter
    private String path;
    @Setter
    private boolean isBegetProtection;

    public Host(String protocol, String server, String path) {
        this.protocol = protocol;
        this.server = server;
        this.path = path;
    }

    public Host(String protocol, String server) {
        this(protocol, server, null);
    }

    public String createUrl() {
        StringBuilder stringBuilder = new StringBuilder(protocol)
                .append("://")
                .append(server)
                .append("/")
                .append(Objects.nonNull(path) ? path : "");
        return stringBuilder.toString();
    }

}
