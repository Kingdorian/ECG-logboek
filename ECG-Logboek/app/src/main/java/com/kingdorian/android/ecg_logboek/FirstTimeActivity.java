package com.kingdorian.android.ecg_logboek;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class FirstTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        TextView view = (TextView)findViewById(R.id.instrIntro);
        System.out.println("hello there");
        Spanned spanned = Html.fromHtml(getString(R.string.instructionsIntro));
        view.setText(spanned);

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView view = (TextView)findViewById(R.id.instrIntro);
        System.out.println("hello there1234");
        Spanned spanned = Html.fromHtml(getString(R.string.instructionsIntro));
        view.setText(spanned);
    }

    public void later(View v) {
        this.finish();
        System.exit(0);
    }
    public void next(View v) {
        setContentView(R.layout.activity_first_time_input);
        //startActivity(i);
    }

    public void finish(View v) {
        EditText ageEdit = (EditText)findViewById(R.id.age);
        EditText weightEdit = (EditText)findViewById(R.id.weight);
        EditText heightEdit = (EditText)findViewById(R.id.length);
        if(!((ageEdit.getText().toString().equals("")||weightEdit.getText().toString().equals("")||heightEdit.getText().toString().equals("")))) {
            System.out.println("First time startup!");

            ActivityData.start();


            ActivityData.setAge(Integer.parseInt(ageEdit.getText().toString()));

            ActivityData.setWeight(Integer.parseInt(weightEdit.getText().toString()));

            ActivityData.setLength(Integer.parseInt(heightEdit.getText().toString()));

            ActivityData.writeData(this);
            Intent i =  new Intent(this, HourlyNotificationService.class);
            startService(i);
            //Intent i = new Intent(this, MainActivity.class);
            this.finish();
            System.exit(0);
        } else {
            Toast toast = Toast.makeText(this, "Een van de velden is niet ingevuld!", Toast.LENGTH_SHORT );
            toast.show();
        }
    }
}

