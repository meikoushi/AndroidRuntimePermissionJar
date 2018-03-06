package com.company.localpushfororeo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;

import java.util.Calendar;

/**
 * Created by shimeikou on 2018/03/02.
 */

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    public static final String CHANNEL_ID = "com.company.localpushfororeo.default";
    public static final String CHANNEL_NAME = "LocalNotification";

    public NotificationUtils(Context base) {
        super(base);
    }

    public void createChannels() {
        NotificationChannel DefaultChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
        DefaultChannel.enableLights(true);
        DefaultChannel.enableVibration(true);
        DefaultChannel.setLightColor(Color.GREEN);
        DefaultChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(DefaultChannel);
    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public Notification.Builder getNotification(String message, PendingIntent intent,int icon) {


        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(message)
                .setSmallIcon(icon)
                .setContentText("")
                .setTicker(message)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(intent)
                .setAutoCancel(true);
    }

    public void Send(int id,Notification notification){
        getManager().notify(id,notification);
    }

}
