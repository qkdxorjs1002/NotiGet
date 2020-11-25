package com.teamhub.notiget.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UnixTimeStampConverter {

    public static String unixToHH(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+9"));

        return dateFormat.format(new Date(timestamp * 1000L));
    }

}
