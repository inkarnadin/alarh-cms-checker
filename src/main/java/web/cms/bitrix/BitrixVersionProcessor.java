package web.cms.bitrix;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import web.analyzer.VersionMap;
import web.analyzer.version.VersionAnalyzer;
import web.cms.CMSType;
import web.http.Request;
import web.parser.TextParser;
import web.parser.XMLParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class BitrixVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final XMLParser<String> xmlParser;
    private final TextParser<String> textParser;
    private final Destination destination;

    private final VersionMap yearMap = new BitrixYearVersionMap();

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, textParser, xmlParser, destination).prepare(protocol, server, CMSType.BITRIX);
        versionAnalyzer.checkViaYear(yearMap, new String[] { "bitrix/admin" }, Pattern.compile("Управление сайтом.*Битрикс, (\\d{4})"));
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
