package web.cms;

import kotlin.Pair;
import web.http.Host;
import web.struct.Destination;
import web.struct.Processor;
import web.struct.assignment.DefaultAssigner;

import java.util.Objects;
import java.util.Optional;

/**
 * Базовый абстрактный процессор для анализа данных.
 *
 * @author inkarnadin
 * on 20-11-2020
 */
public abstract class AbstractCMSProcessor implements Processor<CMSType>, DefaultAssigner {

    protected String protocol;
    protected String server;
    protected Host host;

    /**
     * Метод предварительной настройки процессора.
     *
     * @param protocol протокол взаимодействия
     * @param server адрес сервера
     */
    @Override
    public void configure(String protocol, String server) {
        Objects.requireNonNull(protocol, "Empty protocol value not allowed");
        Objects.requireNonNull(server, "Empty url value not allowed");

        this.protocol = protocol;
        this.server = server;
        this.host = new Host(protocol, server);
    }

    /**
     * Метод обработки признаков определения.
     */
    @Override
    public void process() {}

    /**
     * Метод интерпретации и передачи результата анализа результата.
     *
     * @return результат анализа
     */
    @Override
    public Pair<CMSType, Optional<Destination>> transmit() {
        return null;
    }

}
