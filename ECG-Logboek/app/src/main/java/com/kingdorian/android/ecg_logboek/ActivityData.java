package com.kingdorian.android.ecg_logboek;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.text.format.Time;

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
import java.util.Date;
import java.util.GregorianCalendar;

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

    public ArrayList<HourEntry> getDataArrayList() {
        ArrayList<HourEntry> d = new ArrayList<>();
        for(HourEntry e : data) {
            if(e!=null){
                d.add(e);
            }
        }
        return d;
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
            String result = sb.toString();
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray dataArray = obj.getJSONArray("data");
                for(int i = 0; i < dataArray.length(); i++) {
                    String element = dataArray.getString(i);
                    JSONObject el = new JSONObject(element);
                    data[el.getInt("id")] = new HourEntry(el.getInt("id"), el.getString("description"));
                    System.out.println(data[el.getInt("id")].toJSON());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
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
            obj.put("data", dataArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(obj.toString());
        return obj.toString();
    }

    public long getCurrentHour() {
        Calendar now = new GregorianCalendar();
        return (now.getTimeInMillis() - calendar.getTimeInMillis()) /1000 /3600;
    }


    public Calendar getCalendar() {
        return calendar;
    }

    public int getStartTime(int id) {
        Calendar time = new GregorianCalendar();
        time.setTimeInMillis(calendar.getTimeInMillis() + (1000*3600*(id-1)));
        return time.get(Calendar.HOUR_OF_DAY);
    }
    public int getEndTime(int id) {
        Calendar time = new GregorianCalendar();
        time.setTimeInMillis(calendar.getTimeInMillis() + (1000*3600*id));
        return time.get(Calendar.HOUR_OF_DAY);
    }
}
