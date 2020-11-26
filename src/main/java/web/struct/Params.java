package web.struct;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
@EqualsAndHashCode
public class Params {

    private String protocol = "http";
    private String server;

    public void setServer(String server) {
        this.server = server.trim();
    }

}
