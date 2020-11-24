package web.struct;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
@EqualsAndHashCode
public class Params {

    private String protocol;
    private String server;

}
