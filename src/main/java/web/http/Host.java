package web.http;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@EqualsAndHashCode
public class Host {

    private final String protocol;
    private final String server;

    @Setter
    private String path;
    @Setter
    private String overWrittenBasePath;
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
        return (Objects.nonNull(overWrittenBasePath))
                ? protocol + "://" + overWrittenBasePath
                : protocol + "://" + server + (Objects.nonNull(path) ? "/" + path : "");
    }

}
