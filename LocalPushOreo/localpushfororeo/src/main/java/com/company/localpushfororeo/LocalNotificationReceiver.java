package com.company.localpushfororeo;

import android.content.BroadcastReceiver;

/**
 * Created by shimeikou on 2018/03/03.
 */
import com.unity3d.player.UnityPlayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.app.NotificationCompat;

public class LocalNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String title = "";
        String message = intent.getStringExtra("LocalNotification_Message");
        Integer notificationId = intent.getIntExtra("LocalNotification_Id", 0);

        Intent newIntent = new Intent(context, UnityPlayer.currentActivity.getClass());
        newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        final PackageManager pm = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = pm.getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
            title = applicationInfo.loadLabel(pm).toString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return;
        }

        final int pushIcon = context.getResources().getIdentifier("icon", "drawable", context.getPackageName());

        // make notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent);
        builder.setTicker(message);
        builder.setSmallIcon(pushIcon);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());
    }

}
