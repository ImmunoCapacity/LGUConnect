package com.lguconnect.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationHelper {
    public static final String CHANNEL_DEFAULT = "lgu_default_channel";
    public static final String CHANNEL_EMERGENCY = "lgu_emergency_channel";

    public static void ensureChannels(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        if (manager == null) return;
        NotificationChannel def = new NotificationChannel(CHANNEL_DEFAULT, "LGU Announcements", NotificationManager.IMPORTANCE_DEFAULT);
        def.setDescription("General LGU updates");
        manager.createNotificationChannel(def);
        NotificationChannel em = new NotificationChannel(CHANNEL_EMERGENCY, "Emergency Alerts", NotificationManager.IMPORTANCE_HIGH);
        em.setDescription("Critical emergency broadcasts");
        manager.createNotificationChannel(em);
    }
}
