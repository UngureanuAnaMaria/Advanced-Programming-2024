package lab13.com;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayLocales {
    public static void execute() {
        ResourceBundle messages = ResourceBundle.getBundle("Messages_ro");
        Locale[] locales = Locale.getAvailableLocales();
        StringBuilder output = new StringBuilder();
        output.append(messages.getString("locales")).append("\n");

        for (Locale locale : locales) {
            output.append(locale.toString()).append(": ")
                    .append(locale.getDisplayName()).append("\n");
        }
        System.out.println(output.toString());

        //engleza
        messages = ResourceBundle.getBundle("Messages");
        locales = Locale.getAvailableLocales();
        output = new StringBuilder();
        output.append(messages.getString("locales")).append("\n");

        for (Locale locale : locales) {
            output.append(locale.toString()).append(": ")
                    .append(locale.getDisplayName()).append("\n");
        }
        System.out.println(output.toString());
    }
}
