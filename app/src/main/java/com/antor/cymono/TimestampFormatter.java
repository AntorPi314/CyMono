package com.antor.cymono;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimestampFormatter {

    public static String formatTimestamp(Timestamp timestamp) {
        Date postDate = timestamp.toDate();
        Date currentDate = new Date();

        long differenceInMillis = currentDate.getTime() - postDate.getTime();
        long differenceInHours = differenceInMillis / (1000 * 60 * 60);
        long differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24);

        if (differenceInHours < 24) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            return timeFormat.format(postDate);
        }
        if (differenceInDays < 7) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
            return dayFormat.format(postDate);
        }
        Calendar currentCalendar = Calendar.getInstance();
        Calendar postCalendar = Calendar.getInstance();
        postCalendar.setTime(postDate);

        if (currentCalendar.get(Calendar.YEAR) == postCalendar.get(Calendar.YEAR)) {
            SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMM dd");
            return monthDayFormat.format(postDate);
        }
        SimpleDateFormat yearFormat = new SimpleDateFormat("dd.MM.yy");
        return yearFormat.format(postDate);
    }
}
