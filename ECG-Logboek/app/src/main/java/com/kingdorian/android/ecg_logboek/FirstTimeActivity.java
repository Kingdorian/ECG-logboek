package com.kingdorian.android.ecg_logboek;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Calendar;

public class FirstTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

    }


    public void later(View v) {
        this.finish();
        System.exit(0);
    }
    public void next(View v) {
        System.out.println("First time startup!");

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((cal.getTimeInMillis()/3600000)*3600000 );
        ActivityData.setCalendar(cal);
        System.out.println("StartTime: " + cal.getTime().toString());

        ActivityData.writeData(this);
        Intent i = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(i);
    }
}

