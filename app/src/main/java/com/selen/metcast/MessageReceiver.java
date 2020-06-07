package com.selen.metcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.selen.metcast.data.Constants;

public class MessageReceiver extends BroadcastReceiver {

    private int messageId = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(Constants.STORM_NAME_MSG);
        String title = intent.getStringExtra(Constants.STORM_NAME_MSG_TITLE);
        if (message == null) {
            message = "";
        }
        if (title == null) {
            title = "";
        }
        // создать нотификацию
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }
}
