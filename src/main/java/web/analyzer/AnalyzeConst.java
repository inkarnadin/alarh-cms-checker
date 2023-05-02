package web.analyzer;

import lombok.experimental.UtilityClass;

import static web.http.ContentType.APPLICATION_JAVASCRIPT;
import static web.http.ContentType.APPLICATION_JSON;
import static web.http.ContentType.APPLICATION_XML;
import static web.http.ContentType.APPLICATION_X_JAVASCRIPT;
import static web.http.ContentType.IMAGE_JPG;
import static web.http.ContentType.IMAGE_PNG;
import static web.http.ContentType.TEXT_JAVASCRIPT;
import static web.http.ContentType.TEXT_XML;

/**
 * Вспомогательный класс, который содержит базовые константы, необходимые для анализа.
 *
 * @author inkarnadin
 * on 29-11-2020
 */
@UtilityClass
public class AnalyzeConst {

    public static final String[] BASE_PATH = { "" };

    public static final Integer[] SUCCESS_CODES = { 200, 304 };
    public static final Integer[] DENIED_CODES = { 401, 403 };
    public static final Integer[] ACCEPT_CODES = { 200, 304, 401, 403 };
    public static final Integer[] PHP_DISPLAY_ERROR_MODE_CODES = { 200, 500 };

    public static final String[] SCRIPT_FILES = { APPLICATION_JAVASCRIPT, APPLICATION_X_JAVASCRIPT, TEXT_JAVASCRIPT };
    public static final String[] IMAGE_FILES = { IMAGE_PNG, IMAGE_JPG };
    public static final String[] XML_FILES = { APPLICATION_XML, TEXT_XML };
    public static final String[] JSON_FILES = { APPLICATION_JSON };

}
