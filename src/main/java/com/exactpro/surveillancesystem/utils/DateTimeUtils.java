package com.exactpro.surveillancesystem.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {
    private static  String OLD_FORMAT_DATE = "dd-MM-yyyy";
    private static SimpleDateFormat oldDate = new SimpleDateFormat(OLD_FORMAT_DATE);;

    static {
        oldDate.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static Instant convertDateFormat(String oldDateString) throws ParseException {
        Date d = oldDate.parse(oldDateString);
        
        Timestamp newDate = new java.sql.Timestamp(d.getTime());
        return newDate.toInstant();
    }

    public static String nowDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        String nowDate = formatter.format(date);
        return nowDate;
    }
}
