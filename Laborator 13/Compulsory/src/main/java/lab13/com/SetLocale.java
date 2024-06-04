package lab13.com;

import com.ibm.icu.util.ULocale;

import java.util.Locale;
import java.util.ResourceBundle;

public class SetLocale {
    public static void execute(String languageTag) {
        ResourceBundle messages = ResourceBundle.getBundle("Messages_ro");
        ULocale ulocale = new ULocale(languageTag);
        Locale locale = ulocale.toLocale();
        Locale.setDefault(locale);

        System.out.println(messages.getString("locale.set").replace("{0}", locale.getDisplayName()));
        System.out.println("");

        ResourceBundle messages2 = ResourceBundle.getBundle("Messages");
        System.out.println(messages2.getString("locale.set").replace("{0}", locale.getDisplayName()));
    }
}