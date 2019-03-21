package com.baominh.sdp.utils;

import java.util.Date;

public class DateHelper {

    public static Date unixTimeToDate(String unixTime) {
        Long uTime = Long.parseLong(unixTime);

        Date date = new Date(uTime*1000L);

        return date;
    }

    public static String dateToUnixTime(Date date) {
        Long unixTime = date.getTime()/1000;

        return unixTime.toString();
    }
}
