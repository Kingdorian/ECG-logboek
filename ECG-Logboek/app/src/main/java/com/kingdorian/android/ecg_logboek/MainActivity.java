package com.kingdorian.android.ecg_logboek;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {


    MainListAdapter adapter;
    ActivityData data;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new ActivityData(new GregorianCalendar(2016, 4, 9, 10, 00));
        data.readData(getApplication().getBaseContext());

        ListView listview = (ListView) findViewById(R.id.listView);
        adapter = new MainListAdapter(this, R.layout.activity_main, data.getDataArrayList(), data);
        listview.setAdapter(adapter);

        LayoutInflater li = LayoutInflater.from(this);
        final View promptView = li.inflate(R.layout.prompt, null);
        Resources res = getResources();
        final int hourId = (int) data.getCurrentHour();
        String subTitle = res.getString(R.string.beforeTimeSubTitle);
        subTitle += data.getStartTime(hourId) + ":00" + res.getString(R.string.betweenTimeSubTitle);
        subTitle += data.getEndTime(hourId) + ":00" + res.getString(R.string.afterTimeSubTitle);
        ((TextView) promptView.findViewById(R.id.subTitle)).setText(subTitle);
        if (data.getData()[hourId] != null) {
            ((TextView) promptView.findViewById(R.id.editTextDialogUserInput)).setText(data.getData()[hourId].getDescription());
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(promptView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = ((EditText) promptView.findViewById(R.id.editTextDialogUserInput)).getText().toString();
                data.addHourEntry(new HourEntry(hourId, input));
                data.writeData(getApplication().getBaseContext());
                adapter.setData(data.getDataArrayList());
                adapter.notifyDataSetChanged();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

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
}
