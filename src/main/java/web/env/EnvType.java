package web.env;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.env.php.PhpConnector;
import web.env.phpmyadmin.PhpMyAdminConnector;
import web.env.webserver.WebServerConnector;
import web.env.whois.WhoisConnector;
import web.struct.Connector;

@Getter
@RequiredArgsConstructor
public enum EnvType {

    PHP                     (0, "PHP",          PhpConnector.class),
    PHP_MY_ADMIN            (1, "PhpMyAdmin",   PhpMyAdminConnector.class),
    WEB_SERVER              (2, "WebServer",    WebServerConnector.class),
    WHOIS                   (3, "WhoIs Info",   WhoisConnector.class);

    private final int id;
    private final String name;
    private final Class<? extends Connector> connector;

}
