package com.example.covid.utils;

import org.apache.commons.lang3.time.FastDateFormat;

public abstract class DateUtils {
    public static String formatDate(Long times,String pattern){
        return FastDateFormat.getInstance(pattern).format(times);
    }
}
