package com.baominh.sdp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        long unixSeconds = 1553043442;
        // convert seconds to milliseconds
        Date date = new java.util.Date(unixSeconds*1000L);
// the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
// give a timezone reference for formatting (see comment at the bottom)
        //sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);



    }
}
