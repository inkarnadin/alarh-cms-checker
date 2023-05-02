package web.struct;

import web.cms.CMSType;

/**
 * Компонент работы с состоянием в рамках одной проверочной сессии одного адреса.
 *
 * @author inkarnadin
 * on 02-05-2023
 */
public class Context {

    private static CMSType[] cmsTypes;

    /**
     * Метод обновления состояния контекста.
     *
     * @param cmsTypes список выявленных CMS в результате текущей проверки
     */
    public static void update(CMSType[] cmsTypes) {
        Context.cmsTypes = cmsTypes;
    }

    /**
     * Метод сброса контеста. Должен быть вызван перед началом проверки нового целевого адреса.
     */
    public static void evict() {
        Context.cmsTypes = new CMSType[] { };
    }

}
