package web.struct;

import com.google.inject.Inject;
import web.module.annotation.CMS;
import web.module.annotation.DBAdmin;

import java.util.Objects;
import java.util.Scanner;

public class Runner {

    @Inject @CMS
    Checker cmsChecker;

    @Inject @DBAdmin
    Checker dbAdminChecker;

    public void run() {
        System.out.println("===========================================================");
        System.out.println("Welcome! This is application for check installed plugins for CMS");
        System.out.println("In current time it is support only Joomla CMS and WordPress");
        System.out.println("===========================================================");
        System.out.println("\n");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                Params params = new Params();

                System.out.print("Set protocol [http|https]: ");
                params.setProtocol(scanner.nextLine());

                System.out.print("Set target host [example.com]: ");
                params.setHost(scanner.nextLine());

                System.out.print("Activate PHPMyAdmin module? (y/n): ");
                if (isAnswer(scanner.nextLine())) {
                    dbAdminChecker.check(params);
                    System.out.println("Done!");
                } else {
                    System.out.println("Skipped...");
                }

                System.out.print("Activate CMS module? (y/n): ");
                if (isAnswer(scanner.nextLine())) {
                    System.out.print("Choose CMS type [Joomla|WP]: ");
                    params.setCmsType(scanner.nextLine());

                    cmsChecker.check(params);
                    System.out.println("Done!");
                } else {
                    System.out.println("Skipped...");
                }

                System.out.println("===========================================================");
                System.out.println("\n");
                System.out.print("Try again? (y/n): ");

                if (!isAnswer(scanner.nextLine()))
                    System.exit(1);
            }
        }
    }

    public static boolean isAnswer(final String answer) {
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

}
