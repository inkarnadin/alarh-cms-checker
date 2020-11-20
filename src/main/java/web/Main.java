package web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import web.module.CmsPluginModule;
import web.module.JoomlaProvider;
import web.module.WordPressProvider;

public class Main {

    public static void main(String[] args) {
        System.out.println("===========================================================");
        System.out.println("Welcome! This is application for check installed plugins for CMS");
        System.out.println("In current time it is support only Joomla CMS and WordPress");
        System.out.println("===========================================================");
        System.out.println("\n");

        Injector injector = Guice.createInjector(new CmsPluginModule());
        Provider<Connector> provider = injector.getInstance(WordPressProvider.class);

        Connector connector = provider.get();
        connector.configure("http", "example.com");
        connector.checkPlugins();

        provider = injector.getInstance(JoomlaProvider.class);

        connector = provider.get();
        connector.configure("http", "example.com");
        connector.checkPlugins();

        // CmsPluginChecker.checkPlugins();
    }

}
