package patrisp.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeUtils {
    public static LocalDate parseIsoDate(String isoDate) {
        return LocalDate.parse(isoDate);
    }

    public static String formatDate(String isoDate, String pattern) {
        LocalDate date = parseIsoDate(isoDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        return date.format(formatter);
    }
}
