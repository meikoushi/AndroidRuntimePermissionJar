package com.company.localpushfororeo;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;

/**
 * Created by shimeikou on 2018/03/03.
 */
import com.unity3d.player.UnityPlayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.app.NotificationCompat;
import 	android.os.Build;
import android.util.Log;

public class LocalNotificationReceiver extends BroadcastReceiver {

    NotificationUtils mNotificationUtils;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(mNotificationUtils == null){
            mNotificationUtils = new NotificationUtils(context);
        }

        String title = "";
        String message = intent.getStringExtra("LocalNotification_Message");
        Integer notificationId = intent.getIntExtra("LocalNotification_Id", 0);
        String packageName = intent.getStringExtra("LocalNotification_Message");

        Intent newIntent;
        Activity activity = UnityPlayer.currentActivity;
        if(activity!=null){
            Log.d("currentActivity",activity.getClass().toString());
            newIntent = new Intent(context,activity.getClass());
        }else {
            Log.d("currentActivity","currentActivity is null!");

            PackageManager pm = context.getPackageManager();
            newIntent = pm.getLaunchIntentForPackage(context.getPackageName());
            if (newIntent == null) {
              return;
            }
        }

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


        int icon = context.getResources().getIdentifier("icon", "drawable", context.getPackageName());

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            //念のため、oreo以下は動作維持
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            //builder.setContentIntent(pendingIntent);
            builder.setTicker(message);
            builder.setSmallIcon(icon);
            builder.setContentTitle(title);
            builder.setContentIntent(pendingIntent);
            builder.setContentText(message);
            builder.setWhen(System.currentTimeMillis());
            builder.setDefaults(Notification.DEFAULT_ALL);
            builder.setAutoCancel(true);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, builder.build());
        }else{
            Notification.Builder nb = mNotificationUtils.getNotification(message,pendingIntent,icon);
            mNotificationUtils.Send(notificationId,nb.build());

        }
    }

}
