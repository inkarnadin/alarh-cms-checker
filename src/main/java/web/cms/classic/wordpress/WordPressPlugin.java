package web.cms.classic.wordpress;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WordPressPlugin {

    // wordpress.org
    CF7                         ("contact-form-7",                                              "Contact Form 7"),
    CF7_POPUP_MESSAGE           ("popup-message-for-contact-form-7",                            "Popup Message Contact Form 7"),
    CF7_DRAG_AND_DROP           ("drag-and-drop-multiple-file-upload-contact-form-7",           "Drag and Drop Multiple File Upload – Contact Form 7"),
    CF7_RECAPTCHA               ("contact-form-7-simple-recaptcha",                             "Contact Form 7 Captcha"),
    CF_CFDB7                    ("contact-form-cfdb7",                                          "Contact Form CFDB7"),
    AKISMET                     ("akismet",                                                     "Akismet Spam Protection"),
    WP_PAGE_NAVI                ("wp-pagenavi",                                                 "WP-PageNavi"),
    WOOCOMMERCE                 ("woocommerce",                                                 "WooCommerce"),
    ANTISPAM_BEE                ("antispam-bee",                                                "Antispam Bee"),
    LOGINIZER                   ("loginizer",                                                   "Loginizer"),
    WOO_PAYPAL                  ("woocommerce-gateway-paypal-express-checkout",                 "WooCommerce PayPal Checkout Payment Gateway"),
    WOO_SERVICES                ("woocommerce-services",                                        "WooCommerce Shipping & Tax"),
    CYR_TO_LAT_ENCHANCED        ("cyr3lat",                                                     "Cyr to Lat enhanced"),
    ALL_IN_ONE_SEO              ("all-in-one-seo-pack",                                         "All in One SEO"),
    GOOGLE_XML_SITEMAP          ("google-sitemap-generator",                                    "Google XML Sitemaps"),
    JETPACK                     ("jetpack",                                                     "Jetpack – WP Security, Backup, Speed, & Growth"),
    REALLY_SIMPLE_SSL           ("really-simple-ssl",                                           "Really Simple SSL"),
    REGENERATE_THUMBNAILS       ("regenerate-thumbnails",                                       "Regenerate Thumbnails"),
    ADVANCED_EDITOR_TOOLS       ("tinymce-advanced",                                            "Advanced Editor Tools"),
    W3_TOTAL_CACHE              ("w3-total-cache",                                              "W3 Total Cache"),
    WP_OPTIMIZE                 ("wp-optimize",                                                 "WP-Optimize - Clean, Compress, Cache"),
    WP_SMUSHIT                  ("wp-smushit",                                                  "Smush – Lazy Load Images, Optimize & Compress Images"),
    ENLIGHTER                   ("enlighter",                                                   "Enlighter – Customizable Syntax Highlighter"),
    WP_FEATHERLIGHT             ("wp-featherlight",                                             "WP Featherlight – A Simple jQuery Lightbox"),
    Q2W3_FIXED_WIDGET           ("q2w3-fixed-widget",                                           "Q2W3 Fixed Widget for WordPress"),
    TABLE_OF_CONTENT_PLUS       ("table-of-contents-plus",                                      "Table of Contents Plus"),
    NEXTGEN_GALLERY             ("nextgen-gallery",                                             "WordPress Gallery Plugin – NextGEN Gallery"),
    WP_SPEED_OF_LIGHT           ("wp-speed-of-light",                                           "WP Speed of Light"),
    WP_FANCYBOX                 ("fancybox-for-wordpress",                                      "FancyBox for WordPress"),
    WP_FANCYBOX_3               ("w3dev-fancybox",                                              "fancyBox 3 for WordPress"),
    LIGHTVIEW_PLUS              ("lightview-plus",                                              "Lightview Plus"),
    EASY_FANCYBOX               ("easy-fancybox",                                               "Easy FancyBox"),
    SIMPLE_TAGS                 ("simple-tags",                                                 "Simple Tags – Tags Manager"),
    BB_PRESS                    ("bbpress",                                                     "bbPress"),
    WP_FORMS_LIGHT              ("wpforms-lite",                                                "Contact Form by WPForms – Drag & Drop Form Builder for WordPress"),
    WP_CKEDITOR                 ("ckeditor-for-wordpress",                                      "CKEditor for WordPress"),
    ADD_TO_ANY                  ("add-to-any",                                                  "AddToAny Share Buttons"),
    QUOTES_COLLECTION           ("quotes-collection",                                           "Quotes Collection"),
    MAIL_POET_NEWSLETTERS_2     ("wysija-newsletters",                                          "MailPoet Newsletters (Previous)"),
    MAIL_POET_NEWSLETTERS_3     ("mailpoet",                                                    "MailPoet – emails and newsletters in WordPress"),
    WP_MAIL_LOGGING             ("wp-mail-logging",                                             "WP Mail Logging by MailPoet"),
    ML_SLIDER                   ("ml-slider",                                                   "Slider, Gallery, and Carousel by MetaSlider – Responsive WordPress Plugin"),
    ML_SLIDER_LIGHTBOX          ("ml-slider-lightbox",                                          "MetaSlider Lightbox"),
    ALL_IN_ONE_WP_MIGRATION     ("all-in-one-wp-migration",                                     "All-in-One WP Migration"),
    SG_OPTIMIZER                ("sg-cachepress",                                               "SG Optimizer"),
    WP_SEO                      ("wordpress-seo",                                               "Yoast SEO"),
    DUPLICATOR                  ("duplicator",                                                  "Duplicator - WordPress Migration Plugin"),
    WP_MAIL_SMTP                ("wp-mail-smtp",                                                "WP Mail SMTP by WPForms"),
    WP_GOOGLE_MAPS              ("wp-google-maps",                                              "WP Google Maps"),
    WP_FASTEST_CACHE            ("wp-fastest-cache",                                            "WP Fastest Cache"),
    CLASSIC_EDITOR              ("classic-editor",                                              "Classic Editor"),
    WP_GOOGLE_ANALYTICS_DASH_EM ("google-analytics-dashboard-for-wp",                           "Google Analytics Dashboard for WP by ExactMetrics (formerly GADWP)"),
    WP_GOOGLE_ANALYTICS_DASH_MI ("google-analytics-for-wordpress",                              "Google Analytics Dashboard Plugin for WordPress by MonsterInsights"),
    WP_GOOGLE_ANALYTICS_DASH_A  ("wp-analytify",                                                "Google Analytics Dashboard Plugin for WordPress by Analytify"),
    GOOGLE_SITE_KIT             ("google-site-kit",                                             "Site Kit by Google"),
    ANALYTIFY_DASHBOARD_WIDGET  ("analytify-analytics-dashboard-widget",                        "Google Analytics Dashboard Widget by Analytify"),
    REDIRECTION                 ("redirection",                                                 "Redirection"),
    WP_SUPER_CACHE              ("wp-super-cache",                                              "WP Super Cache"),
    AUTOPTIMIZE                 ("autoptimize",                                                 "Autoptimize"),
    BREADCRUMB_NAVXT            ("breadcrumb-navxt",                                            "Breadcrumb NavXT"),
    USER_ROLE_EDITOR            ("user-role-editor",                                            "User Role Editor"),
    ADMINIMIZE                  ("adminimize",                                                  "Adminimize"),
    SVG_SUPPORT                 ("svg-support",                                                 "SVG Support"),
    FLAMINGO                    ("flamingo",                                                    "Flamingo"),
    SIMPLE_301_REDIRECTS        ("simple-301-redirects",                                        "Simple 301 Redirects"),
    WP_FILE_MANAGER             ("wp-file-manager",                                             "WP File Manager"),
    WP_SITEMAP_PAGE             ("wp-sitemap-page",                                             "WP Sitemap Page"),
    DUPLICATE_POST              ("copy-delete-posts",                                           "Duplicate Post"),
    YOAST_DUPLICATE_POST        ("duplicate-post",                                              "Yoast Duplicate Post"),
    ELEMENTOR                   ("elementor",                                                   "Elementor Website Builder"),
    WP_IMPORTER                 ("wordpress-importer",                                          "WordPress Importer"),
    BACK_WP_UP                  ("backwpup",                                                    "BackWPup – WordPress Backup Plugin"),
    PRETTY_LINK                 ("pretty-link",                                                 "Pretty Links – Link Management, Branding, Tracking & Sharing Plugin"),
    WIDGET_LOGIC                ("widget-logic",                                                "Widget Logic"),
    CLOUDFLARE                  ("cloudflare",                                                  "Cloudflare"),
    HEADER_FOOTER               ("header-footer",                                               "Head, Footer and Post Injections"),
    LOGIN_LOCKDOWN              ("login-lockdown",                                              "Login LockDown"),
    WP_CERBER                   ("wp-cerber",                                                   "Cerber Security, Anti-spam & Malware Scan"),
    AMP                         ("amp",                                                         "AMP"),
    BROKEN_LINK_CHECKER         ("broken-link-checker",                                         "Broken Link Checker"),
    EWWW_IMAGE_OPTIMIZER        ("ewww-image-optimizer",                                        "EWWW Image Optimizer"),
    WP_POST_RATINGS             ("wp-postratings",                                              "WP-PostRatings"),
    WP_JOB_MANAGER              ("wp-job-manager",                                              "WP Job Manager"),
    EASY_UPDATE_MANAGER         ("stops-core-theme-and-plugin-updates",                         "Easy Updates Manager"),
    WPS_HIDE_LOGIN              ("wps-hide-login",                                              "WPS Hide Login"),
    TABLE_PRESS                 ("tablepress",                                                  "TablePress"),
    BACK_UP_WP                  ("backupwordpress",                                             "BackUpWordPress"),
    HELLO_DOLLY                 ("hello-dolly",                                                 "Hello Dolly"),
    LIMIT_LOGIN_ATTEMPTS        ("limit-login-attempts-reloaded",                               "Limit Login Attempts Reloaded"),
    WORDFENCE                   ("wordfence",                                                   "Wordfence Security"),
    IWP_CLIENT                  ("iwp-client",                                                  "InfiniteWP - Client"),
    CHILD_THEME_CONFIGURATOR    ("child-theme-configurator",                                    "Child Theme Configurator"),
    BETTER_SEARCH_REPLACE       ("better-search-replace",                                       "Better Search Replace"),
    LOCO_TRANSLATE              ("loco-translate",                                              "Loco Translate"),
    MAINTENANCE                 ("maintenance",                                                 "Maintenance"),
    WP_MAILCHIMP                ("mailchimp-for-wp",                                            "MC4WP: Mailchimp for WordPress"),
    REDUX_FRAMEWORK             ("redux-framework",                                             "Redux – Gutenberg Blocks Library & Framework"),
    EASY_WP_SMTP                ("easy-wp-smtp",                                                "Easy WP SMTP"),
    WOO_FACEBOOK                ("facebook-for-woocommerce",                                    "Facebook for WooCommerce"),
    INTUITIVE_CUSTOM_POST_ORDER ("intuitive-custom-post-order",                                 "Intuitive Custom Post Order"),
    DISABLE_COMMENTS            ("disable-comments",                                            "Disable Comments – Remove Comments & Protect From Spam"),
    WOO_BLOCK                   ("woo-gutenberg-products-block",                                "WooCommerce Blocks"),
    WOO_LOGIN_LIGHT             ("woocommerce-login-and-registration",                          "Woocommerce Login / Signup Lite"),
    WOO_PRODUCT_FILTER          ("woocommerce-products-filter",                                 "WOOF - Products Filter for WooCommerce"),
    WOO_VAR_SWATCHES            ("woo-variation-swatches",                                      "Variation Swatches for WooCommerce"),
    WOO_CAROUSEL_SLIDER         ("woo-product-carousel-slider-and-grid-ultimate",               "WooCommerce Product Carousel, Slider & Grid Ultimate"),
    WOO_SMART_COUPONS           ("wt-smart-coupons-for-woocommerce",                            "Smart Coupons for WooCommerce"),
    WOO_SAPHALI                 ("saphali-woocommerce-lite",                                    "Saphali Woocommerce Russian"),
    WOO_PDF                     ("woocommerce-pdf-invoices-packing-slips",                      "WooCommerce PDF Invoices & Packing Slips"),
    WOO_CONFIRMATION_EMAIL      ("woo-confirmation-email",                                      "User Email Verification for WooCommerce"),
    WOO_AFFILIATES_PLUGIN       ("sumoaffiliates",                                              "SUMO Affiliates - WooCommerce Affiliate System"),
    WP_POLLS                    ("wp-polls",                                                    "WP-Polls"),
    REALLY_SAMPLE_CAPTCHA       ("really-simple-captcha",                                       "Really Simple CAPTCHA"),
    CUSTOM_POST_TYPE_UI         ("custom-post-type-ui",                                         "Custom Post Type UI"),
    OPTIN_MONSTER               ("optin-monster",                                               "Marketing Toolkit by OptinMonster – Popups, Email Optin Forms & Newsletter Subscribers"),
    OPTINMONSTER                ("optinmonster",                                                "Marketing Toolkit by OptinMonster (alter)"),
    WP_USER_AVATAR              ("wp-user-avatar",                                              "WP User Avatar"),
    YARPP                       ("yet-another-related-posts-plugin",                            "Yet Another Related Posts Plugin (YARPP)"),
    CALL_NOW_BUTTON             ("call-now-button",                                             "Call Now Button"),
    KIRKI                       ("kirki",                                                       "Kirki Customizer Framework"),
    CONTACT_WIDGETS             ("contact-widgets",                                             "Contact Widgets"),
    FORCE_REGENERATE_THUMBNAILS ("force-regenerate-thumbnails",                                 "Force Regenerate Thumbnails"),
    INSTAGRAM_FEED              ("instagram-feed",                                              "Smash Balloon Social Photo Feed"),
    NINJA_FORMS                 ("ninja-forms",                                                 "Ninja Forms Contact Form – The Drag and Drop Form Builder for WordPress"),
    UPDRAFT_PLUS                ("updraftplus",                                                 "UpdraftPlus - Backup/Restore"),
    WORKER                      ("worker",                                                      "ManageWP Worker"),
    TAXONOMY_TERMS_ORDER        ("taxonomy-terms-order",                                        "Category Order and Taxonomy Terms Order"),
    BETTER_WP_SECURITY          ("better-wp-security",                                          "iThemes Security (formerly Better WP Security)"),
    NEXTEND_FACEBOOK_CONNECT    ("nextend-facebook-connect",                                    "Nextend Social Login and Register"),
    WP_MAINTENANCE_MODE         ("wp-maintenance-mode",                                         "WP Maintenance Mode"),
    PHP_CODE_WIDGET             ("php-code-widget",                                             "PHP Code Widget"),
    SIMPLE_COLORBOX             ("simple-colorbox",                                             "Simple Colorbox"),
    SHORTCODES_ULTIMATE         ("shortcodes-ultimate",                                         "WordPress Shortcodes Plugin — Shortcodes Ultimate"),
    TINY_COMPRESS_IMAGES        ("tiny-compress-images",                                        "Compress JPEG & PNG images"),
    ADMIN_MENU_EDITOR           ("admin-menu-editor",                                           "Admin Menu Editor"),
    NEWSLETTER                  ("newsletter",                                                  "Newsletter"),
    ALL_IN_ONE_WP_SECURITY      ("all-in-one-wp-security-and-firewall",                         "All In One WP Security & Firewall"),
    DUPLICATE_PAGE              ("duplicate-page",                                              "Duplicate Page"),
    WP_DISQUS                   ("disqus-comment-system",                                       "Disqus for WordPress"),
    ULTIMATE_SOCIAL_MEDIA_ICONS ("ultimate-social-media-icons",                                 "Social Media Share Buttons Popup & Pop Up Social Sharing Icons"),
    BEAUTIFUL_TAXONOMY_FILTER   ("beautiful-taxonomy-filters",                                  "Beautiful taxonomy filters"),
    POPUP_MAKER                 ("popup-maker",                                                 "Popup Maker – Popup for opt-ins, lead gen, & more"),
    COOKIE_NOTICE               ("cookie-notice",                                               "Cookie Notice & Compliance for GDPR / CCPA"),
    PLAYER_JS                   ("playerjs",                                                    "PlayerJS"),
    EASY_AFFILIATE_LINKS        ("easy-affiliate-links",                                        "Easy Affiliate Links"),
    NOCAPTCHA_RECAPTCHA         ("advanced-nocaptcha-recaptcha",                                "Advanced noCaptcha & invisible Captcha (v2 & v3)"),
    GOOGLE_RECAPTCHA            ("google-captcha",                                              "reCaptcha by BestWebSoft"),
    CLEANTALK_SPAM_PROTECT      ("cleantalk-spam-protect",                                      "Spam protection, AntiSpam, FireWall by CleanTalk"),
    SITEORIGIN_PANELS           ("siteorigin-panels",                                           "Page Builder by SiteOrigin"),
    GUTENBERG                   ("gutenberg",                                                   "Gutenberg"),
    HYPER_CACHE                 ("hyper-cache",                                                 "Hyper Cache"),

    // others
    JS_COMPOSER                 ("js_composer",                                                 "WPBakery Page Builder WordPress plugin"),
    ULTIMATE_VC_ADDONS          ("Ultimate_VC_Addons",                                          "Ultimate Addons for WPBakery Page Builder"),
    FAST_MEDIA_GALLERY          ("fastmediagallery",                                            "Fast Media Gallery For Visual Composer"),
    FAST_GALLERY_VC             ("fastgallery_vc",                                              "Fast Gallery for Visual Composer"),
    FAST_GALLERY                ("fastgallery",                                                 "Fast Gallery - Premium Wordpress Plugin"),
    REVSLIDER                   ("revslider",                                                   "Slider Revolution Responsive WordPress Plugin"),
    SITEPRESS_MULTILING_CMS     ("sitepress-multilingual-cms",                                  "WPML Sitepress Multilingual Cms"),
    WP_POST_VIEW                ("wp-postviews",                                                "WP-PostViews"),
    ELEMENTOR_PRO               ("elementor-pro",                                               "Elementor PRO WordPress Page Builder"),
    ULTIMATE_ELEMENTOR          ("ultimate-elementor",                                          "Ultimate Addons for Elementor"),
    ARROW_PRESS_CORE            ("arrowpress-core",                                             "ArrowPress Shortcodes"),
    BOOKLY_ADDON_PRO            ("bookly-addon-pro",                                            "Bookly PRO – Appointment Booking and Scheduling Software System"),
    AR_CONTACTUS                ("ar-contactus",                                                "All in One Support Button && Callback Request"),
    QTRANSLATE_X                ("qtranslate-x",                                                "QTranslate X"),
    ;

    private final String path;
    private final String name;

    public static String search(String path) {
        for (WordPressPlugin plugin : values()) {
            if (plugin.path.equals(path)) {
                return plugin.name;
            }
        }
        return "";
    }

}
