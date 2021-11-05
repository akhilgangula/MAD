package com.example.hw05;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Util {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getFormattedDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return localDateTime.format(myFormatObj);
    }
}
