package com.company.localpushoreo;

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
    public static final String CHANNEL_ID = "com.company.kendama.default";
    public static final String CHANNEL_NAME = "DEFAULT CHANNEL";
    public static final String CHANNEL_ID2 = "com.company.kendama.high";
    public static final String CHANNEL_NAME2 = "HIGH CHANNEL";

    public NotificationUtils(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels() {

        NotificationChannel DefaultChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
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

    public void Send(int id,Notification notification){
        getManager().notify(id,notification);
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

    public void SendNotification(Context context,int notificationId, String message,String anthor, int Seconds )
    {
       // 時間をセットする
        Calendar calendar = Calendar.getInstance();
        // Calendarを使って現在の時間をミリ秒で取得
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 5秒後に設定
        calendar.add(Calendar.SECOND, Seconds);

        //明示的なBroadCast
        Intent intent = new Intent(context,AlarmBroadcastReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, 0);

        intent.putExtra( "LocalNotification_Id", notificationId );
        intent.putExtra( "LocalNotification_Title", message );
        intent.putExtra( "LocalNotification_Message", anthor );
        intent.putExtra( "LocalNotification_PackageName", context.getPackageName() );

        PendingIntent sender = PendingIntent.getBroadcast( context,  notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT );
        AlarmManager alarm = (AlarmManager)context.getSystemService( Context.ALARM_SERVICE );
        alarm.setExact( AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , sender );

    }


    public void CancelNotification(Context context,int notificationId )
    {

        Intent intent = new Intent( context, AlarmBroadcastReceiver.class );
        intent.setAction( "com.company.localpushfororeo.action" );

        PendingIntent pendingIntent = PendingIntent.getBroadcast( context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT );

        AlarmManager alramManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alramManager.cancel( pendingIntent );
        pendingIntent.cancel();
    }

}
