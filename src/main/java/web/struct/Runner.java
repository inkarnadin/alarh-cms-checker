package web.struct;

import com.google.inject.Inject;
import com.google.inject.Injector;
import web.cms.CMSChecker;
import web.env.EnvironmentChecker;

import java.util.Objects;
import java.util.Scanner;

public class Runner {

    @Inject
    Injector injector;

    public void run() {
        System.out.println("===========================================================");
        System.out.println("Welcome! This is application for check info about different CMS");
        System.out.println("In current time it is support next systems:");
        System.out.println(" * Joomla! (version)");
        System.out.println(" * WordPress (version)");
        System.out.println(" * Yii Framework");
        System.out.println(" * DataLife Engine (version)");
        System.out.println(" * MaxSite CMS");
        System.out.println(" * Drupal (version)");
        System.out.println(" * MODx");
        System.out.println(" * 1C-Bitrix (version)");
        System.out.println(" * Tilda");
        System.out.println(" * Lavarel");
        System.out.println(" * VamShop");
        System.out.println(" * Nuxt.js");
        System.out.println(" * Magento");
        System.out.println(" * OpenCart CMS");
        System.out.println(" * InSales");
        System.out.println(" * Vigbo CMS");
        System.out.println(" * RailsOnRuby");
        System.out.println(" * Vue.js");
        System.out.println(" * uKit");
        System.out.println(" * Shopify");
        System.out.println(" * Moguta.CMS (version)");
        System.out.println("===========================================================");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                Params params = new Params();

                printSplit();

                System.out.print("Set target host [example.com]: ");
                params.setServer(scanner.nextLine());
                printSplit();

                System.out.print("Activate CMS module? (y/n): ");
                if (isAnswer(scanner.nextLine())) {
                    injector.getInstance(CMSChecker.class).check(params);
                    System.out.println("Done!");
                } else {
                    System.out.println("Skipped...");
                }
                printSplit();

                System.out.print("Activate additional information module? (y/n): ");
                if (isAnswer(scanner.nextLine())) {
                    injector.getInstance(EnvironmentChecker.class).check(params);
                    System.out.println("Done!");
                } else {
                    System.out.println("Skipped...");
                }
                printSplit();

                System.out.print("Try again? (y/n): ");

                if (!isAnswer(scanner.nextLine()))
                    System.exit(1);
            }
        }
    }

    private static boolean isAnswer(final String answer) {
        if (Objects.isNull(answer))
            return false;

        switch (answer.toLowerCase()) {
            case "y":
            case "yes":
                return true;
            case "n":
            case "no":
            default:
                return false;
        }
    }

    private static void printSplit() {
        System.out.println("===========================================================\n");
    }

}
