package lab13.app;

import lab13.com.DisplayLocales;
import lab13.com.Info;
import lab13.com.SetLocale;

import java.util.Scanner;

public class LocaleExplore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input command: ");
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("displaylocales")) {
                DisplayLocales.execute();
            } else if (command.toLowerCase().startsWith("setlocale")) {
                String languageTag = command.substring(command.indexOf(" ") + 1);
                SetLocale.execute(languageTag);
            } else if (command.toLowerCase().startsWith("info")) {
                String languageTag = command.substring(command.indexOf(" ") + 1);
                Info.execute(languageTag);
            } else if (command.equalsIgnoreCase("exit")) {
                break;
            } else {
                System.out.println("Unknown command");
            }
        }
        scanner.close();
    }
}