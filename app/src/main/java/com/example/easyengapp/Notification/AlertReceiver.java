package com.example.easyengapp.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper nh = new NotificationHelper(context);
        NotificationCompat.Builder nb = nh.getChannelNotification();
        nh.getManager().notify(1, nb.build());
    }
}
