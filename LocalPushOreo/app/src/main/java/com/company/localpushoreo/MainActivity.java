package com.company.localpushoreo;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private NotificationUtils mNotificationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationUtils = new NotificationUtils(this);
        final EditText editTextTime = (EditText) findViewById(R.id.Time_lag);
        final EditText editTextTitleDefault = (EditText) findViewById(R.id.default_title);
        final EditText editTextAuthorDefault = (EditText) findViewById(R.id.default_text);
        Button buttonDefault = (Button) findViewById(R.id.send_default);

        buttonDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int seconds = Integer.parseInt(editTextTime.getText().toString());

                String title = editTextTitleDefault.getText().toString();
                String author = editTextAuthorDefault.getText().toString();

                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(author)) {
                    Notification.Builder nb = mNotificationUtils.getNotification(title,author,false);

                    mNotificationUtils.SendNotification(getApplicationContext(),101,title,author,seconds);
                }
            }
        });

        final EditText editTextTitleHigh = (EditText) findViewById(R.id.high_title);
        final EditText editTextAuthorHigh = (EditText) findViewById(R.id.high_text);
        Button buttonHigh = (Button) findViewById(R.id.send_high);

        buttonHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitleHigh.getText().toString();
                String author = editTextAuthorHigh.getText().toString();

                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(author)) {
                    Notification.Builder nb = mNotificationUtils.getNotification(title, author,true);
                    mNotificationUtils.Send(102,nb.build());
                }
            }
        });

        Button buttonAndroidNotifSettings = (Button) findViewById(R.id.btn_android_notif_settings);
        buttonAndroidNotifSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(i);
            }
        });

    }

}
