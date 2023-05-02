package web.cms.classic.bitrix;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import web.cms.AbstractCMSProcessor;
import web.http.Request;
import web.http.ResponseBodyHandler;
import web.printer.Printer;
import web.struct.ResultContainer;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static web.analyzer.AnalyzeConst.PHP_DISPLAY_ERROR_MODE_CODES;
import static web.printer.PrinterMarker.LIST_PRINTER;

/**
 * Процессор обработки и вывода общей информации по CMS Bitrix.
 *
 * @author inkarnadin
 * on 02-05-2023
 */
// FIXME этот функционал должен быть перенесен в раздел "Environment" - дополнительно выявлять атрибуты сервера через средства CMS
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class BitrixInfoProcessor extends AbstractCMSProcessor {

    private final ResultContainer resultContainer;
    private final Request request;
    @Named(LIST_PRINTER)
    private final Printer printer;

    private static final String[] DISPLAY_ERROR_ON_CHECK_CRITERIA = {
            "bitrix/components/bitrix/main.ui.selector/templates/.default/template.php",
            "bitrix/admin/restore_export.php",
            "bitrix/bitrix.php",
            "bitrix/themes/.default/.description.php",
    };

    /**
     * Метод получения и вывода общений информации по CMS.
     */
    @Override
    public void process() {
        resultContainer.add(0, "  ** Data:");
        showPhpDisplayErrorsMode().ifPresent(it -> resultContainer.add(1, String.format("   * PHP display error mode: %s", it)));
        showLocalPath().ifPresent(it -> resultContainer.add(2, String.format("   * Local path: %s", it)));

        printer.print(resultContainer);
    }

    /**
     * Метод, который проверяет включен ли вывод ошибок на уровне PHP ({@code display_errors = On}).
     *
     * @return {@code ON} если включен, иначе {@code OFF}
     */
    private Optional<String> showPhpDisplayErrorsMode() {
        for (String path : DISPLAY_ERROR_ON_CHECK_CRITERIA) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                if (Arrays.asList(PHP_DISPLAY_ERROR_MODE_CODES).contains(response.code()) && ResponseBodyHandler.readBody(response).contains("Fatal error")) {
                    return Optional.of("ON");
                }
            }
        }
        return Optional.of("OFF");
    }

    /**
     * Метод, который возвращает локальный путь на сервере до места расположения файлов ресурса.
     *
     * @return абсолютный локальный путь
     */
    private Optional<String> showLocalPath() {
        for (String path : DISPLAY_ERROR_ON_CHECK_CRITERIA) {
            host.setPath(path);
            try (Response response = request.send(host)) {
                String body = ResponseBodyHandler.readBody(response);
                if (Arrays.asList(PHP_DISPLAY_ERROR_MODE_CODES).contains(response.code()) && body.contains("Fatal error")) {
                    Matcher matcher = Pattern.compile("thrown in (.*?)/bitrix").matcher(body);
                    if (matcher.find()) {
                        return Optional.of(matcher.group(1));
                    }
                }
            }
        }
        return Optional.empty();
    }

}
