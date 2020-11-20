package web.cms;

import web.Connector;

import java.util.Scanner;

public class CMSChecker {

    public static void check() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Choose CMS type [Joomla|WP]: ");
                String cms = scanner.nextLine().toLowerCase();

                System.out.print("Set protocol [http|https]: ");
                String protocol = scanner.nextLine();

                System.out.print("Set target host [example.com]: ");
                String host = scanner.nextLine();

                Connector connector = CMSFactory.getCMSConnector(cms);
                connector.configure(protocol, host);

                System.out.print("Check plugins? (y/n): ");
                String checkPlugin = scanner.nextLine();

                if ("y".equalsIgnoreCase(checkPlugin)) {
                    connector.checkPlugins();
                } else {
                    System.out.println("Skipped...");
                }

                System.out.println("===========================================================");
                System.out.println("\n");
                System.out.print("Try again? (y/n): ");
                String answer = scanner.nextLine();

                if ("n".equalsIgnoreCase(answer) || "no".equalsIgnoreCase(answer))
                    System.exit(1);
            }
        }
    }

}
