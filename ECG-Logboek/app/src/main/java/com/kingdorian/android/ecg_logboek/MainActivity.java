package com.kingdorian.android.ecg_logboek;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    MainListAdapter adapter;
    static ActivityData data;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            data.readData(getApplication().getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (data == null ) {
            firstStartup();
        }

        ListView listview = (ListView) findViewById(R.id.listView);
        adapter = new MainListAdapter(this, R.layout.activity_main, data.getDataArrayList(), data);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int id, long arg3) {
                        DialogFragment dialog = new EditEntryDialog(data, 1);
                        dialog.show(getFragmentManager(), "Edit hour entry");

                    }
                });

        LayoutInflater li = LayoutInflater.from(this);
        final View promptView = li.inflate(R.layout.prompt, null);
        Resources res = getResources();
        final int hourId = (int) data.getCurrentHour();
        String subTitle = res.getString(R.string.beforeTimeSubTitle);
        subTitle += data.getStartTime(hourId) + ":00" + res.getString(R.string.betweenTimeSubTitle);
        subTitle += data.getEndTime(hourId) + ":00" + res.getString(R.string.afterTimeSubTitle);
        ((TextView) promptView.findViewById(R.id.subTitle)).setText(subTitle);
        if (hourId<47&&data.getData()[hourId] != null) {
            ((TextView) promptView.findViewById(R.id.editTextDialogUserInput)).setText(data.getData()[hourId].getDescription());
        } else if (hourId > 47) {
            data.clearData(this);
        }

        DialogFragment dialog = new EditEntryDialog(data, 1);
        dialog.show(getFragmentManager(), "Edit hour entry");
        adapter.notifyDataSetChanged();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }



    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.kingdorian.android.ecg_logboek/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();


        data.writeData(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.kingdorian.android.ecg_logboek/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void firstStartup() {
        System.out.println("First time startup!");

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((cal.getTimeInMillis()/3600000)*3600000 );
        System.out.println("StartTime: " + cal.getTime().toString());
        Intent intent = new Intent(this, HourlyNotificationService.class);
        PendingIntent pendingintent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60*60*1000, pendingintent);

        data = new ActivityData(cal);
        data.writeData(this);
    }
}
