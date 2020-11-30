package web.cms.wordpress;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import web.cms.CMSType;
import web.analyzer.version.VersionAnalyzer;
import web.http.Request;
import web.parser.TextParser;
import web.struct.AbstractProcessor;
import web.struct.Destination;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class WordPressVersionProcessor extends AbstractProcessor {

    private final Request request;
    private final TextParser<String> parser;
    private final Destination destination;

    @Override
    public void process() {
        VersionAnalyzer versionAnalyzer = new VersionAnalyzer(request, parser, null, destination);
        versionAnalyzer.prepare(protocol, server, CMSType.WORDPRESS);
        versionAnalyzer.checkViaMainPageGenerator(new Pattern[] {
                Pattern.compile("<meta name=\"[gG]enerator\" content=\"WordPress\\s(.*?)\" />")
        });
        versionAnalyzer.checkViaSinceScript(Pattern.compile("@since\\s(.*?)\\s"), new String[] {
                "wp-includes/js/admin-bar.js",
                "wp-includes/js/api-request.js",
                "wp-includes/js/autosave.js",
                "wp-includes/js/comment-reply.js",
                "wp-includes/js/customize-base.js",
                "wp-includes/js/customize-preview.js",
                "wp-includes/js/customize-preview-nav-menus.js",
                "wp-includes/js/customize-preview-widgets.js",
                "wp-includes/js/customize-selective-refresh.js",
                "wp-includes/js/heartbeat.js",
                "wp-includes/js/media-audiovideo.js",
                "wp-includes/js/media-grid.js",
                "wp-includes/js/media-views.js",
                "wp-includes/js/wp-auth-check.js",
                "wp-includes/js/wp-backbone.js",
                "wp-includes/js/wp-embed.js",
                "wp-includes/js/wp-emoji.js",
                "wp-includes/js/wp-emoji-loader.js",
                "wp-includes/js/wp-pointer.js",
        });
    }

    @Override
    public Optional<Destination> transmit() {
        return destination.isFull() ? Optional.of(destination) : Optional.empty();
    }

}
