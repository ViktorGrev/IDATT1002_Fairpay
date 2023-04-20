package no.ntnu.idatt1002.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * A utility class for dates.
 */
public final class DateUtil {

    /**
     * Returns a date from an epoch time with the specified pattern.
     * @param   epoch the epoch time to format
     * @param   pattern the date pattern
     * @return  a date from an epoch time with the specified pattern
     */
    public static String format(long epoch, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return format.format(epoch);
    }
}
