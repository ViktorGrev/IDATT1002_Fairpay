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

    /**
     * Returns the difference between the current time and the specified
     * timestamp as a formatted string.
     * @param   timestamp the timestamp to subtract
     * @return  the difference between the current time and the specified
     * timestamp as a formatted string
     */
    public static String getElapsedTime(long timestamp) {
        String date;
        long diff = (System.currentTimeMillis() - timestamp) / 1000;
        if(diff < 60) {
            date = diff + " second" + (diff > 1 ? "s" : "") + " ago";
        } else if(diff < 60 * 60) {
            long min = (diff / 60);
            date = min + " minute" + (min > 1 ? "s" : "") + " ago";
        } else if(diff < 60 * 60 * 24) {
            long hours = (diff / (60 * 60));
            date = hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        } else {
            long days = (diff / (60 * 60 * 24));
            date = days + " day" + (days > 1 ? "s" : "") + " ago";
        }
        return date;
    }
}
