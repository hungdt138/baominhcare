package com.baominh.sdp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.baominh.sdp.utils.DateHelper;

public class Test {
    public static void main(String[] args) {
        long unixSeconds = 1477877421;
        // convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds*1000L);
// the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
// give a timezone reference for formatting (see comment at the bottom)
        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);

//        Calendar calendarNow = Calendar.getInstance();
//        calendarNow.setTime(new Date());
//
//        System.out.println("HH: " + calendarNow.get(Calendar.HOUR_OF_DAY));
//        System.out.println("MM: " + calendarNow.get(Calendar.MINUTE));
//
//        System.out.println("DAY: " + calendarNow.get(Calendar.DAY_OF_WEEK));
//
//        System.out.println("WEEK: " + calendarNow.get(Calendar.WEEK_OF_MONTH));

    	Date date1 = new Date();
    	System.out.println(DateHelper.dateToUnixTime(date1));
    }
}
