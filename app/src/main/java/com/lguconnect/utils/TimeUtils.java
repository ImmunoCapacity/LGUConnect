package com.lguconnect.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static String relativeTime(long millis) {
        long diff = System.currentTimeMillis() - millis;
        if (diff < 60_000) return "just now";
        if (diff < 3_600_000) return (diff / 60_000) + "m ago";
        if (diff < 86_400_000) return (diff / 3_600_000) + "h ago";
        return (diff / 86_400_000) + "d ago";
    }

    public static String formatDate(long millis) {
        return new SimpleDateFormat("MMMM dd, yyyy · h:mm a", Locale.getDefault()).format(new Date(millis));
    }

    public static String formatShortDate(long millis) {
        return new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date(millis));
    }
}
