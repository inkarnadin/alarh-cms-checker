package web.struct;

import com.google.inject.Inject;
import web.module.annotation.Cms;
import web.module.annotation.DBAdmin;

import java.util.Objects;
import java.util.Scanner;

public class Runner {

    @Inject @Cms
    Checker cmsChecker;

    @Inject @DBAdmin
    Checker dbAdminChecker;

    public void run() {
        System.out.println("===========================================================");
        System.out.println("Welcome! This is application for check info about different CMS");
        System.out.println("In current time it is support next systems:");
        System.out.println(" * Joomla!");
        System.out.println(" * WordPress");
        System.out.println(" * Yii Framework");
        System.out.println("===========================================================");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                Params params = new Params();

                printSplit();

                System.out.print("Set protocol [http|https]: ");
                params.setProtocol(scanner.nextLine());

                System.out.print("Set target host [example.com]: ");
                params.setServer(scanner.nextLine());

                printSplit();

                System.out.print("Activate PHPMyAdmin module? (y/n): ");
                if (isAnswer(scanner.nextLine())) {
                    dbAdminChecker.check(params);
                    System.out.println("Done!");
                } else {
                    System.out.println("Skipped...");
                }

                printSplit();

                System.out.print("Activate CMS module? (y/n): ");
                if (isAnswer(scanner.nextLine())) {
                    cmsChecker.check(params);
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
        System.out.println("===========================================================");
        System.out.println("\n");
    }

}
