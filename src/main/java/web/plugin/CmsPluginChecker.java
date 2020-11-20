package web.plugin;

import web.Processor;

import java.util.Scanner;

public class CmsPluginChecker {

    public static void checkPlugins() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                Processor processor;

                System.out.print("Choose CMS type [Joomla|WP]: ");
                String cms = scanner.nextLine().toLowerCase();

                System.out.print("Set protocol [http|https]: ");
                String protocol = scanner.nextLine();

                System.out.print("Set target host [example.com]: ");
                String host = scanner.nextLine();

//                switch (CmsType.search(cms)) {
//                    case JOOMLA:
//                        //processor = new JoomlaCheckComponentProcessor(protocol, host);
//                        break;
//                    case WORDPRESS:
//                        processor = new WordPressCheckPluginProcessor(protocol, host);
//                        break;
//                    default:
//                        throw new IllegalArgumentException("Unsupported CMS type");
//                }
//                processor.process();

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
