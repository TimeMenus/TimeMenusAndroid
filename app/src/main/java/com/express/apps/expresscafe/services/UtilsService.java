package com.express.apps.expresscafe.services;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fabdin on 8/11/2016.
 */
public class UtilsService {


    public static String getTodayDate(){
        Date date = new Date();
        String mon = new SimpleDateFormat("MMM").format(date);
        String year = new SimpleDateFormat("yy").format(date);
        String day = new SimpleDateFormat("d").format(date);
        String dayWthSufx = getDayOfMonthSuffix(Integer.parseInt(day));
        String todayDate = mon + " " + dayWthSufx + " " + year;
        return todayDate;
    }

    private static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return n+"th";
        }
        switch (n % 10) {
            case 1:  return n+"st";
            case 2:  return n+"nd";
            case 3:  return n+"rd";
            default: return n+"th";
        }
    }

}
