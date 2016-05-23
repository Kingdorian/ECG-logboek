package com.kingdorian.android.ecg_logboek;

import android.content.Context;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * Created by dorian on 9-5-16.
 */
public class ActivityData {

    private static HourEntry[] data = new HourEntry[24];
    private static Calendar calendar;
    private static int length, weight, age;

    private static boolean started = false;

    static String FILENAME = "activityData";

    public static void setCalendar(Calendar cal) {
        calendar = cal;
    }

    public static HourEntry[] getData() {
        return data;
    }

    public static ArrayList<HourEntry> getDataArrayList() {
        ArrayList<HourEntry> d = new ArrayList<HourEntry>();
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
        String dateString = obj.get("startTime").toString().replace(" CEST ", " GMT+0200 ");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        try{
            calendar.setTime(sdf.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        started = Boolean.parseBoolean(obj.get("started").toString());
        length= Integer.parseInt(obj.get("length").toString());
        age = Integer.parseInt(obj.get("age").toString());
        weight = Integer.parseInt(obj.get("weight").toString());
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
            obj.put("started", started);
            obj.put("length", length);
            obj.put("weight", weight);
            obj.put("age", age);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(obj.toString());
        return obj.toString();
    }

    public static long getCurrentHour() {
        Calendar now = Calendar.getInstance();
            return (now.getTimeInMillis() -

                    calendar.getTimeInMillis()) / 1000 / 3600;
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
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(calendar.getTimeInMillis() + (1000*3600*(id-1)));
        return time.get(Calendar.HOUR_OF_DAY);
    }
    public static int getEndTime(int id) {
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(calendar.getTimeInMillis() + (1000*3600*id));
        return time.get(Calendar.HOUR_OF_DAY);
    }

    public static long getStartTimeMillis() {
        return calendar.getTimeInMillis();
    }

    public static long getStartDay() { return getStartTimeMillis()/(1000*60*60*24);}

    public static boolean started() {
        return started;
    }

    public static void start() {
        calendar = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        calendar.setTimeInMillis(((cal.getTimeInMillis()/3600000))*3600000 );
        started = true;
    }

    public static void setLength(int l) {
        length = l;
    }
    public static void setWeight(int w) {
        weight = w;
    }
    public static void setAge(int a) {
        age = a;
    }
}
