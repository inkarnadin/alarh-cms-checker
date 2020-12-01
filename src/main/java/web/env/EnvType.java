package web.env;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnvType {

    PHP(0, "PHP"),
    PHP_MY_ADMIN(1, "PhpMyAdmin");

    private final int id;
    private final String name;

}
