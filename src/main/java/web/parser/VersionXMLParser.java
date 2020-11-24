package web.parser;

import lombok.Getter;
import lombok.SneakyThrows;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;

public class VersionXMLParser implements XMLParser<String> {

    private String version = "unknown";

    @Override
    @SneakyThrows
    public String parse(String txt) {
        InputSource inputSource = new InputSource(new ByteArrayInputStream(txt.getBytes()));

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SAXParser saxParser = factory.newSAXParser();
        JoomlaXMLConfigHandler handler = new JoomlaXMLConfigHandler();
        saxParser.parse(inputSource, handler);

        return version;
    }

    class JoomlaXMLConfigHandler extends DefaultHandler {

        private boolean isVersion = false;

        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equalsIgnoreCase("version"))
                isVersion = true;
        }

        public void endElement(String uri, String localName, String qName) {}

        public void characters(char[] ch, int start, int length) {
            if (isVersion) {
                version = new String(ch, start, length);
                isVersion = false;
            }
        }

    }

}
