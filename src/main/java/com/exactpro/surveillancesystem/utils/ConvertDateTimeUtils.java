package com.exactpro.surveillancesystem.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ConvertDateTimeUtils {
    private static final String OLD_FORMAT_DATE = "dd-MM-yyyy";
    private static SimpleDateFormat oldDate = new SimpleDateFormat(OLD_FORMAT_DATE);;

    public static Instant convertDateFormat(String oldDateString) throws ParseException {
        Date d = oldDate.parse(oldDateString);
        Timestamp newDate = new java.sql.Timestamp(d.getTime());
        return newDate.toInstant();
    }

}
