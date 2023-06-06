package com.ifsc.julio.javatcc.util;

import java.util.Calendar;
import java.util.Date;
import static java.util.Calendar.*;

public class DateUtil {

    public static Date getStartOfDay(Date date) {
        Calendar calendar = getInstance();
        calendar.setTime(date);
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = getInstance();
        calendar.setTime(date);

        calendar.set(HOUR_OF_DAY, 23);
        calendar.set(MINUTE, 59);
        calendar.set(SECOND, 59);
        calendar.set(MILLISECOND, 999);

        return calendar.getTime();
    }
}
