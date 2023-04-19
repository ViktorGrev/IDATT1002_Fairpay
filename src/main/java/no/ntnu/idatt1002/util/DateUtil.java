package no.ntnu.idatt1002.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class DateUtil {

    public static String format(long l, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return format.format(l);
    }
}
