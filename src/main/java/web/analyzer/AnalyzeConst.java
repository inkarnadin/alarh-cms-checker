package web.analyzer;

import lombok.experimental.UtilityClass;

import static web.http.ContentType.*;

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
    public static final String[] IMAGE_FILES = { IMAGE_PNG, IMAGE_JPG, IMAGE_SVG, IMAGE_SVG_XML };
    public static final String[] XML_FILES = { APPLICATION_XML, TEXT_XML };
    public static final String[] JSON_FILES = { APPLICATION_JSON };

}
