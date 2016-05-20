package com.kingdorian.android.ecg_logboek;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

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
        int notifcationid = 001;
        Resources res = this.getResources();
        NotificationManager notifyManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .setContentTitle(res.getString(R.string.notificationTitle))
                        .setContentText(res.getString(R.string.notificationTitle));
        notifyManager.notify(notifcationid, mBuilder.build());
    }
}
