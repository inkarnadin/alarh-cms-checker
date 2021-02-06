package web.cms.wordpress;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WordPressPlugin {

    // wordpress.org
    CF7                     ("contact-form-7",                                              "Contact Form 7"),
    CF7_POPUP_MESSAGE       ("popup-message-for-contact-form-7",                            "Popup Message Contact Form 7"),
    CF7_DRAG_AND_DROP       ("drag-and-drop-multiple-file-upload-contact-form-7",           "Drag and Drop Multiple File Upload – Contact Form 7"),
    AKISMET                 ("akismet",                                                     "Akismet Spam Protection"),
    WP_PAGE_NAVI            ("wp-pagenavi",                                                 "WP-PageNavi"),
    WOOCOMMERCE             ("woocommerce",                                                 "WooCommerce"),
    ANTISPAM_BEE            ("antispam-bee",                                                "Antispam Bee"),
    LOGINIZER               ("loginizer",                                                   "Loginizer"),
    WOOCOMMERCE_PAYPAL      ("woocommerce-gateway-paypal-express-checkout",                 "WooCommerce PayPal Checkout Payment Gateway"),
    WOOCOMMERCE_SERVICES    ("woocommerce-services",                                        "WooCommerce Shipping & Tax"),
    CYR_TO_LAT_ENCHANCED    ("cyr3lat",                                                     "Cyr to Lat enhanced"),
    ALL_IN_ONE_SEO          ("all-in-one-seo-pack",                                         "All in One SEO"),
    GOOGLE_XML_SITEMAP      ("google-sitemap-generator",                                    "Google XML Sitemaps"),
    JETPACK                 ("jetpack",                                                     "Jetpack – WP Security, Backup, Speed, & Growth"),
    REALLY_SIMPLE_SSL       ("really-simple-ssl",                                           "Really Simple SSL"),
    REGENERATE_THUMBNAILS   ("regenerate-thumbnails",                                       "Regenerate Thumbnails"),
    ADVANCED_EDITOR_TOOLS   ("tinymce-advanced",                                            "Advanced Editor Tools"),
    W3_TOTAL_CACHE          ("w3-total-cache",                                              "W3 Total Cache"),
    WP_OPTIMIZE             ("wp-optimize",                                                 "WP-Optimize - Clean, Compress, Cache"),
    WP_SMUSHIT              ("wp-smushit",                                                  "Smush – Lazy Load Images, Optimize & Compress Images"),
    ENLIGHTER               ("enlighter",                                                   "Enlighter – Customizable Syntax Highlighter"),
    WP_FEATHERLIGHT         ("wp-featherlight",                                             "WP Featherlight – A Simple jQuery Lightbox"),
    Q2W3_FIXED_WIDGET       ("q2w3-fixed-widget",                                           "Q2W3 Fixed Widget for WordPress"),
    TABLE_OF_CONTENT_PLUS   ("table-of-contents-plus",                                      "Table of Contents Plus"),
    NEXTGEN_GALLERY         ("nextgen-gallery",                                             "WordPress Gallery Plugin – NextGEN Gallery"),
    WP_SPEED_OF_LIGHT       ("wp-speed-of-light",                                           "WP Speed of Light"),
    WP_FANCYBOX             ("fancybox-for-wordpress",                                      "FancyBox for WordPress"),
    LIGHTVIEW_PLUS          ("lightview-plus",                                              "Lightview Plus"),
    EASY_FANCYBOX           ("easy-fancybox",                                               "Easy FancyBox"),
    SIMPLE_TAGS             ("simple-tags",                                                 "Simple Tags – Tags Manager"),
    BB_PRESS                ("bbpress",                                                     "bbPress"),
    WP_FORMS_LIGHT          ("wpforms-lite",                                                "Contact Form by WPForms – Drag & Drop Form Builder for WordPress"),
    WP_CKEDITOR             ("ckeditor-for-wordpress",                                      "CKEditor for WordPress"),
    ADD_TO_ANY              ("add-to-any",                                                  "AddToAny Share Buttons"),
    QUOTES_COLLECTION       ("quotes-collection",                                           "Quotes Collection"),
    MAIL_POET_NEWSLETTERS_2 ("wysija-newsletters",                                          "MailPoet Newsletters (Previous)"),
    MAIL_POET_NEWSLETTERS_3 ("mailpoet",                                                    "MailPoet – emails and newsletters in WordPress"),
    WP_MAIL_LOGGING         ("wp-mail-logging",                                             "WP Mail Logging by MailPoet"),
    ML_SLIDER               ("ml-slider",                                                   "Slider, Gallery, and Carousel by MetaSlider – Responsive WordPress Plugin"),
    ML_SLIDER_LIGHTBOX      ("ml-slider-lightbox",                                          "MetaSlider Lightbox"),
    ALL_IN_ONE_WP_MIGRATION ("all-in-one-wp-migration",                                     "All-in-One WP Migration"),
    SG_OPTIMIZER            ("sg-cachepress",                                               "SG Optimizer"),
    WP_SEO                  ("wordpress-seo",                                               "Yoast SEO"),
    DUPLICATOR              ("duplicator",                                                  "Duplicator - WordPress Migration Plugin"),
    WP_MAIL_SMTP            ("wp-mail-smtp",                                                "WP Mail SMTP by WPForms"),

    // others
    JS_COMPOSER             ("js_composer",                                                 "WPBakery Page Builder WordPress plugin"),
    REVSLIDER               ("revslider",                                                   "Slider Revolution Responsive WordPress Plugin"),
    SITEPRESS_MULTILING_CMS ("sitepress-multilingual-cms",                                  "WPML Sitepress Multilingual Cms"),
    WP_POST_VIEW            ("wp-postviews",                                                "WP-PostViews"),
    ;

    private final String path;
    private final String name;

    public static String search(String path) {
        for (WordPressPlugin plugin : values())
            if (plugin.path.equals(path))
                return plugin.name;
        return "";
    }

}
