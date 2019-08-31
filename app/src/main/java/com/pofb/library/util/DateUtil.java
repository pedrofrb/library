package com.pofb.library.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String convertToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static Date convertToDate(String date) {
        Date convert = null;
        try {
            convert = new SimpleDateFormat("dd/MM/yy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return convert;
    }
}
