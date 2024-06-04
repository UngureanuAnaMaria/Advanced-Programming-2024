package lab13.com;

import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.Currency;
import com.ibm.icu.text.DateFormatSymbols;

import java.text.SimpleDateFormat;

import java.util.Locale;
import java.util.ResourceBundle;

public class Info {
    public static void execute(String languageCode) {
        ULocale locale = new ULocale(languageCode);
        ResourceBundle messages = ResourceBundle.getBundle("Messages_ro");
        String infoMessage = messages.getString("info");

        System.out.println(infoMessage + " " + locale + ":");
        System.out.println("Tara: " + locale.getDisplayCountry() + " (" + locale.getCountry() + ")");
        System.out.println("Limba: " + locale.getDisplayLanguage() + " (" + locale.getLanguage() + ")");
        try {
            Currency currency = Currency.getInstance(locale.toLocale());
            System.out.println("Moneda: " + currency.getCurrencyCode() + " (" + currency.getDisplayName(locale.toLocale()) + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("Moneda: N/A");
        }

        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
        String[] weekdays = dateFormatSymbols.getWeekdays();
        System.out.print("Zilele saptamanii: ");
        for (int i = 2; i < weekdays.length; i++) {
            System.out.print(weekdays[i]);
            if (i < weekdays.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        String[] months = dateFormatSymbols.getMonths();
        System.out.print("Lunile: ");
        for (int i = 0; i < months.length; i++) {
            System.out.print(months[i]);
            if (i < months.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        Locale l_locale = locale.toLocale();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM, yyyy", l_locale);
        String currentDate = dateFormat.format(new java.util.Date());
        System.out.println("Azi: " + currentDate);
        System.out.println("");

        //engleza
        locale = new ULocale(languageCode);
        messages = ResourceBundle.getBundle("Messages");
        infoMessage = messages.getString("info");

        System.out.println(infoMessage + " " + locale + ":");
        System.out.println("Country: " + locale.getDisplayCountry() + " (" + locale.getCountry() + ")");
        System.out.println("Language: " + locale.getDisplayLanguage() + " (" + locale.getLanguage() + ")");
        try {
            Currency currency = Currency.getInstance(locale.toLocale());
            System.out.println("Currency: " + currency.getCurrencyCode() + " (" + currency.getDisplayName(locale.toLocale()) + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("Currency: N/A");
        }

        dateFormatSymbols = new DateFormatSymbols(locale);
        weekdays = dateFormatSymbols.getWeekdays();
        System.out.print("Week Days: ");
        for (int i = 2; i < weekdays.length; i++) {
            System.out.print(weekdays[i]);
            if (i < weekdays.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        months = dateFormatSymbols.getMonths();
        System.out.print("Months: ");
        for (int i = 0; i < months.length; i++) {
            System.out.print(months[i]);
            if (i < months.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        l_locale = locale.toLocale();
        dateFormat = new SimpleDateFormat("d MMMM, yyyy", l_locale);
        currentDate = dateFormat.format(new java.util.Date());
        System.out.println("Today: " + currentDate);
    }
}