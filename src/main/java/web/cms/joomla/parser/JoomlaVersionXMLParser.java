package web.cms.joomla.parser;

import lombok.Getter;
import lombok.SneakyThrows;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import web.struct.Parser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;

public class JoomlaVersionXMLParser implements Parser {

    @Override
    @SneakyThrows
    public String parse(String text) {
        InputSource inputSource = new InputSource(new ByteArrayInputStream(text.getBytes()));

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SAXParser saxParser = factory.newSAXParser();
        JoomlaXMLConfigHandler handler = new JoomlaXMLConfigHandler();
        saxParser.parse(inputSource, handler);

        return handler.getVersion();
    }

    static class JoomlaXMLConfigHandler extends DefaultHandler {

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

}
