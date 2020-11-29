package web.cms.wordpress;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import web.cms.CMSType;
import web.analyzer.check.MainPageAnalyzer;
import web.analyzer.check.PageAnalyzer;
import web.analyzer.check.PathAnalyzer;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;
import web.http.Request;

import java.util.*;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WordPressCheckProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<Boolean> parser;
    private final Destination destination;

    @Override
    public void process() {
        List<Boolean> result = new ArrayList<>();

        MainPageAnalyzer mainPageAnalyzer = new MainPageAnalyzer(request, parser).prepare(protocol, server, result);
        mainPageAnalyzer.checkViaMainPageGenerator(new String[]{
                "WordPress"
        });

        PathAnalyzer pathAnalyzer = new PathAnalyzer(request).prepare(protocol, server, result);
        pathAnalyzer.checkViaPaths(new Integer[] { 200, 403 }, new String[] {
                "wp-content",
                "wp-includes"
        });

        PageAnalyzer pageAnalyzer = new PageAnalyzer(request, parser).prepare(protocol, server, result);
        pageAnalyzer.checkViaPageKeywords(new String[] { "wp-login.php" }, new Pattern[] {
                Pattern.compile("Powered by .*WordPress"),
                Pattern.compile("wp-pwd"),
                Pattern.compile("wp-submit"),
                Pattern.compile("wp-core-ui")
        });
        pageAnalyzer.checkViaPageCookies(new String[] { "wp-login.php" }, Pattern.compile("wordpress_test_cookie"));

        long count = result.stream().filter(b -> b).count();
        if (count > 0)
            destination.insert(0, String.format(
                    successMessage,
                    CMSType.WORDPRESS.getName(),
                    count,
                    result.size())
            );
    }


    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
