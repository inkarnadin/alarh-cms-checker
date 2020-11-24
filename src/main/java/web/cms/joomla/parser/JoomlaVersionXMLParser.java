package web.cms.joomla.parser;

import lombok.SneakyThrows;
import org.xml.sax.InputSource;
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

}
