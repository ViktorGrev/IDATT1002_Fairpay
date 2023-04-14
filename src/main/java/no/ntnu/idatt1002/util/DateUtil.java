package no.ntnu.idatt1002.util;

import java.text.SimpleDateFormat;

public final class DateUtil {

    public static String format(long l, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(l);
    }
}
