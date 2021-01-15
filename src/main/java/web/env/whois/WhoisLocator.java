package web.env.whois;

import web.http.Host;

import java.util.StringJoiner;

public class WhoisLocator {

    private final static String address = "ip-api.com";
    private final static String format = "json";

    public static Host getAvailableWhoisHost(String protocol, String server) {
        StringJoiner stringJoiner = new StringJoiner("/").add(address).add(format).add(server);
        return new Host(protocol, stringJoiner.toString());
    }

}
