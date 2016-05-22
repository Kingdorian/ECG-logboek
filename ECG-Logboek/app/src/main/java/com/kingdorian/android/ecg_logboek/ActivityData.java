package com.kingdorian.android.ecg_logboek;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.test.ActivityInstrumentationTestCase2;
import android.text.format.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by dorian on 9-5-16.
 */
public class ActivityData {

    private static HourEntry[] data = new HourEntry[48];
    private static Calendar calendar;

    static String FILENAME = "activityData";

    public static void setCalendar(Calendar cal) {
        calendar = cal;
    }

    public static HourEntry[] getData() {
        return data;
    }

    public static ArrayList<HourEntry> getDataArrayList() {
        ArrayList<HourEntry> d = new ArrayList<>();
        for(int i = 0; i < data.length; i++) {
            if(data[i]!=null){
                d.add(data[i]);
            } else {
                HourEntry newEntry = new HourEntry(i, "");
                d.add(newEntry);
                data[i] = newEntry;
            }
        }
        return d;
    }

    public static void addHourEntry(HourEntry hr) {
        data[hr.getId()] = hr;
    }

    public static void writeData(Context ctx) {

        try {
            FileOutputStream fos = ctx.openFileOutput(FILENAME, ctx.MODE_PRIVATE);
            fos.write(getDataJSON().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void readData(Context ctx) throws IOException, JSONException {

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
        JSONObject obj = new JSONObject(result);
        calendar = Calendar.getInstance();
        calendar.setTime(new Date(obj.get("startTime").toString()));
        System.out.println("Start time in file: " + obj.get("startTime"));
        JSONArray dataArray = obj.getJSONArray("data");
        for(int i = 0; i < dataArray.length(); i++) {
            String element = dataArray.getString(i);
            JSONObject el = new JSONObject(element);
            data[el.getInt("id")] = new HourEntry(el.getInt("id"), el.getString("description"));
            System.out.println(data[el.getInt("id")].toJSON());
        }

    }

    public static String getDataJSON() {
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

    public static long getCurrentHour() {
        Calendar now = new GregorianCalendar();
        return (now.getTimeInMillis() - calendar.getTimeInMillis()) /1000 /3600;
    }

    public static void clearData(Context ctx) {
        try {
            FileOutputStream fos = ctx.openFileOutput(FILENAME, ctx.MODE_PRIVATE);
            fos.write("".getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Uri getDataFileURI(Context ctx) throws URISyntaxException {
        return Uri.parse(ctx.getFilesDir() + File.pathSeparator + FILENAME);
    }


    public Calendar getCalendar() {
        return calendar;
    }

    public static int getStartTime(int id) {
        Calendar time = new GregorianCalendar();
        time.setTimeInMillis(calendar.getTimeInMillis() + (1000*3600*(id-1)));
        return time.get(Calendar.HOUR_OF_DAY);
    }
    public static int getEndTime(int id) {
        Calendar time = new GregorianCalendar();
        time.setTimeInMillis(calendar.getTimeInMillis() + (1000*3600*id));
        return time.get(Calendar.HOUR_OF_DAY);
    }

    public static long getStartTimeMillis() {
        return calendar.getTimeInMillis();
    }

    public static long getStartDay() { return getStartTimeMillis()/(1000*60*60*24);}
}
