package com.kingdorian.android.ecg_logboek;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dorian on 9-5-16.
 */
public class ActivityData {

    private HourEntry[] data = new HourEntry[48];
    private Calendar calendar;

    String FILENAME = "activityData";

    public ActivityData(Calendar calender) {
        this.calendar = calender;
    }

    public HourEntry[] getData() {
        return data;
    }

    public void addHourEntry(HourEntry hr) {
        data[hr.getId()] = hr;
    }

    public void writeData(Context ctx) {

        try {
            FileOutputStream fos = ctx.openFileOutput(FILENAME, ctx.MODE_PRIVATE);
            fos.write(getDataJSON().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData(Context ctx) {
        try {
            FileInputStream fis = ctx.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader buffered = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = buffered.readLine()) != null) {
                sb.append(line);
            }
            buffered.close();
            isr.close();
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDataJSON() {
        JSONObject obj = new JSONObject();
        JSONArray dataArray = new JSONArray();
        for(HourEntry entry : data) {
            if(entry != null) {
                dataArray.put(entry.toJSON());
            }
        }
        try {
            obj.put("startTime", calendar.getTime().toString());
            obj.put("data", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(dataArray.toString());
        return dataArray.toString();
    }

}
