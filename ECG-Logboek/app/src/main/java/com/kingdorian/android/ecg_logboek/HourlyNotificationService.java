package com.kingdorian.android.ecg_logboek;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by dorian on 17-5-16.
 */
public class HourlyNotificationService extends IntentService {

    public HourlyNotificationService() {
        super("HourlyNotificationService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("starting service");
        if(ActivityData.getData() == null) {
            try {
                ActivityData.readData(this);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        int hourId = (int)ActivityData.getCurrentHour();
        Resources res = this.getResources();
        NotificationManager notifyManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        String contentText = res.getString(R.string.beforeTimeSubTitle);
        contentText += ActivityData.getStartTime(hourId) + ":00" + res.getString(R.string.betweenTimeSubTitle);
        contentText += ActivityData.getEndTime(hourId) + ":00" + res.getString(R.string.afterTimeSubTitle);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .setContentTitle(res.getString(R.string.notificationTitle))
                        .setContentText(contentText)
                        .setAutoCancel(true);
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        notifyManager.notify(hourId, mBuilder.build());
        this.stopSelf();
    }
}
