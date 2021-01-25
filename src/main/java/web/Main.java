package web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import web.module.MainModule;
import web.struct.Runner;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new MainModule());
        injector.getInstance(Runner.class).run();
    }

}
