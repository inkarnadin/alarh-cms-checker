package web;

import web.joomla.processor.JoomlaCheckComponentProcessor;
import web.wordpress.processor.WordPressCheckPluginProcessor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("===========================================================");
        System.out.println("Welcome! This is application for check installed plugins for CMS");
        System.out.println("In current time it is support only Joomla CMS and WordPress");
        System.out.println("===========================================================");
        System.out.println("\n");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                IProcessor processor;

                System.out.print("Choose CMS type [Joomla|WP]: ");
                String cms = scanner.nextLine();

                System.out.print("Set protocol [http|https]: ");
                String protocol = scanner.nextLine();

                System.out.print("Set target host [example.com]: ");
                String host = scanner.nextLine();

                if ("Joomla".equalsIgnoreCase(cms)) {
                    processor = new JoomlaCheckComponentProcessor(protocol, host);
                } else if ( "WP".equalsIgnoreCase(cms)) {
                    processor = new WordPressCheckPluginProcessor(protocol, host);
                } else {
                    throw new IllegalArgumentException("Unsupported CMS type");
                }
                processor.process();

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
