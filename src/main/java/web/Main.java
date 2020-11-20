package web;

import web.plugin.CmsPluginChecker;

public class Main {

    public static void main(String[] args) {
        System.out.println("===========================================================");
        System.out.println("Welcome! This is application for check installed plugins for CMS");
        System.out.println("In current time it is support only Joomla CMS and WordPress");
        System.out.println("===========================================================");
        System.out.println("\n");

        CmsPluginChecker.check();
    }

}
