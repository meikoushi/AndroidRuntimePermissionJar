package com.company.localpushfororeo;

/**
 * Created by shimeikou on 2018/03/03.
 */

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.unity3d.player.UnityPlayer;

public class LocalPushPlugin {

    public static void ChannelInit(){
        Context context = UnityPlayer.currentActivity.getApplicationContext();
        NotificationUtils notificationUtils = new NotificationUtils(context);
        notificationUtils.createChannels();
    }

    public static void SendNotification( int notificationId, String message, long unixtime )
    {
        // インテント作成
        Context context = UnityPlayer.currentActivity.getApplicationContext();

        Intent intent = new Intent( context, LocalNotificationReceiver.class );
        intent.putExtra( "LocalNotification_Id", notificationId );
        intent.putExtra( "LocalNotification_Message", message );
        intent.putExtra( "LocalNotification_PackageName", UnityPlayer.currentActivity.getPackageName() );
        intent.setAction( "com.company.localpushfororeo.action" );

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis( unixtime );

        PendingIntent sender = PendingIntent.getBroadcast( context,  notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT );
        AlarmManager alarm = (AlarmManager)context.getSystemService( Context.ALARM_SERVICE );
        alarm.setExact( AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , sender );
    }

    public static void CancelNotification( int notificationId )
    {
        Context context = UnityPlayer.currentActivity.getApplicationContext();

        Intent intent = new Intent( context, LocalNotificationReceiver.class );
        intent.setAction( "com.company.localpushfororeo.action" );

        PendingIntent pendingIntent = PendingIntent.getBroadcast( context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT );

        AlarmManager alramManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alramManager.cancel( pendingIntent );
        pendingIntent.cancel();
    }
}