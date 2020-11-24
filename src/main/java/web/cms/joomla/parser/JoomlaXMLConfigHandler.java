package web.cms.joomla.parser;

import lombok.Getter;
import lombok.SneakyThrows;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

class JoomlaXMLConfigHandler extends DefaultHandler {

    @Getter
    private String version = "unknown";

    private boolean isVersion = false;

    @SneakyThrows
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("version"))
            isVersion = true;
    }

    @SneakyThrows
    public void endElement(String uri, String localName, String qName) {}

    @SneakyThrows
    public void characters(char[] ch, int start, int length) {
        if (isVersion) {
            version = new String(ch, start, length);
            isVersion = false;
        }
    }

}