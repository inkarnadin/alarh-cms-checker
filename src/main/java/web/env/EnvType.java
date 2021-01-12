package web.env;

import com.google.inject.Provider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.module.provider.PhpMyAdminProvider;
import web.module.provider.PhpProvider;
import web.module.provider.WebServerProvider;
import web.struct.Connector;

@Getter
@RequiredArgsConstructor
public enum EnvType {

    PHP                     (0, "PHP",          PhpProvider.class),
    PHP_MY_ADMIN            (1, "PhpMyAdmin",   PhpMyAdminProvider.class),
    WEB_SERVER              (2, "WebServer",    WebServerProvider.class);

    private final int id;
    private final String name;
    private final Class<? extends Provider<Connector>> provider;

}
