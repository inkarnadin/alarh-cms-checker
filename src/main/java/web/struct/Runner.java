package web.struct;

import com.google.inject.Inject;
import com.google.inject.Injector;
import web.cms.CMSChecker;
import web.env.EnvironmentChecker;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Основной компонент приложения.
 * <p>Представляет начальное консольное меню для управления сканированием и отображает перечень возможностей.
 *
 * @author inkarnadin
 * on 21-11-2020
 */
public class Runner {

    @Inject
    Injector injector;

    /**
     * Start scanning lifecycle.
     */
    public void run() {
        System.out.println("===========================================================");
        try {
            Files.readAllLines(Path.of("src/main/resources/description.txt")).forEach(System.out::println);
        } catch (Exception xep) {
            xep.printStackTrace();
            System.out.println("No description: " + xep.getClass());
        }
        System.out.println("=======================DEBUG-MODE==========================");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                Context.evict();
                Params params = new Params();

                printSplit();

                System.out.print("Set target host [example.com]: ");
                String inputString = scanner.nextLine();
                String[] args = inputString.split("\\s+");

                readPreferences(args);

                params.setServer(args[0]);
                printSplit();

                if (Preferences.isEnableMainModule()) {
                    System.out.println("~CMS module activated~");
                    injector.getInstance(CMSChecker.class).check(params);
                    System.out.println("Done!");
                } else {
                    System.out.println("Skipped...");
                }
                printSplit();

                if (Preferences.isEnableExtendModule()) {
                    System.out.println("~Extend info module activated~");
                    injector.getInstance(EnvironmentChecker.class).check(params);
                    System.out.println("Done!");
                } else {
                    System.out.println("Skipped...");
                }
                printSplit();
            }
        }
    }

    private static void readPreferences(String[] args) {
        Preferences.manageMainModule(!Arrays.asList(args).contains("-mm"));
        Preferences.manageExtendModule(!Arrays.asList(args).contains("-em"));
        Preferences.manageLowImportanceFilter(Arrays.asList(args).contains("-li"));
        Preferences.manageWhoIsInfo(Arrays.asList(args).contains("-who"));
        Preferences.manageThemeFullInfo(Arrays.asList(args).contains("-th"));
    }

    private static void printSplit() {
        System.out.println("===========================================================\n");
    }

}
