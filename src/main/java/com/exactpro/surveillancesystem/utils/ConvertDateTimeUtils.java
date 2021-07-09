package com.exactpro.surveillancesystem.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static java.lang.Double.parseDouble;

public class ConvertDateTimeUtils {
    private static final String OLD_FORMAT = "dd-MM-yyyy hh:mm:ss";
    private static final String OLD_FORMAT1 = "dd-MM-yyyy";
    private static SimpleDateFormat sdf;

    public static Instant convertDateFormatTransactions(String oldDateString) throws ParseException {
        sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = sdf.parse(oldDateString);
        Timestamp timestamp = new java.sql.Timestamp(d.getTime());
        return timestamp.toInstant();
    }

    public LocalDate convertDateFormatPrices(String dateConvert) throws ParseException {
        sdf = new SimpleDateFormat(OLD_FORMAT1);
        Date d = sdf.parse(dateConvert);
        Timestamp timestamp = new java.sql.Timestamp(d.getTime());
        return timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static double toDouble(String str){
        double d = parseDouble(str);
        return d;
    }
}
