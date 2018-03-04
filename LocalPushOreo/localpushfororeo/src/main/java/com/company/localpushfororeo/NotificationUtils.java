package com.company.localpushfororeo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

/**
 * Created by shimeikou on 2018/03/02.
 */

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    public static final String CHANNEL_ID = "com.company.kendama.default";
    public static final String CHANNEL_NAME = "DEFAULT CHANNEL";
    public static final String CHANNEL_ID2 = "com.company.kendama.high";
    public static final String CHANNEL_NAME2 = "HIGH CHANNEL";

    public NotificationUtils(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels() {

        NotificationChannel DefaultChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
        DefaultChannel.enableLights(true);
        DefaultChannel.enableVibration(true);
        DefaultChannel.setLightColor(Color.GREEN);
        DefaultChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(DefaultChannel);

        NotificationChannel HighChannel = new NotificationChannel(CHANNEL_ID2,CHANNEL_NAME2,NotificationManager.IMPORTANCE_HIGH);
        HighChannel.enableLights(true);
        HighChannel.enableVibration(true);
        HighChannel.setLightColor(Color.RED);
        HighChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getManager().createNotificationChannel(HighChannel);

    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public Notification.Builder getNotification(String title, String body,boolean isHigh) {
        String channelID = CHANNEL_ID;
        if (isHigh) {
            channelID = CHANNEL_ID2;
        }
        return new Notification.Builder(getApplicationContext(), channelID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setAutoCancel(true);
    }


}
