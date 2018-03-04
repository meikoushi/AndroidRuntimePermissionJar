package com.company.localpushoreo;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by shimeikou on 2018/03/04.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationUtils mNotificationUtils= new NotificationUtils(context);
        Integer notificationId = intent.getIntExtra("LocalNotification_Id", 0);
        String message = intent.getStringExtra("LocalNotification_Title");
        String anthor = intent.getStringExtra("LocalNotification_Message");
        String packageName = intent.getStringExtra("LocalNotification_PackageName");

        Notification.Builder nb = mNotificationUtils.getNotification(message,anthor,true);
        mNotificationUtils.Send(111,nb.build());
    }
}
