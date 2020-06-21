package com.example.easyengapp.Notification;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.easyengapp.R;

public class NotificationHelper extends ContextWrapper {

    public static final String channelId = "channel1";
    public static final String channelName = "Channel1";
    private NotificationManager nManager;

    public NotificationHelper(Context base) {
        super(base);
        createChannel();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (nManager == null) {
            nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return nManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle("Notification from EasyEng App!!")
                .setContentText("Time to practice!")
                .setSmallIcon(R.drawable.ic_launcher_background);
    }
}
